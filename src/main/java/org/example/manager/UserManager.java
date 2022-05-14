package org.example.manager;

import lombok.AllArgsConstructor;
import org.example.authenticator.Authenticator;
import org.example.dto.request.UserRegisterRequestDTO;
import org.example.dto.response.UserMeResponseDTO;
import org.example.dto.response.UserRegisterResponseDTO;
import org.example.dto.response.UserRemoveByIdResponseDTO;
import org.example.dto.response.UserRestoreByIdResponseDTO;
import org.example.exception.*;
import org.example.authentication.Authentication;
import org.example.dto.response.UserGetAllResponseDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class UserManager {

    private NamedParameterJdbcTemplate template;

    private Authenticator authenticator;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserGetAllResponseDTO> getAll(int limit, long offset) throws InvalidLimitException, InvalidOffsetException, NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        Authentication authentication = authenticator.authenticate();
        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }
        if (limit > 50) {
            throw new InvalidLimitException();
        }
        if(limit <= 0){
            throw  new InvalidLimitException();
        }
        if(offset < 0){
            throw new InvalidOffsetException();
        }
        return template.query(
                // language=PostgreSQL
                """
                        SELECT id, login, role FROM users
                        WHERE removed = FALSE
                        ORDER BY id
                        LIMIT :limit OFFSET :offset
                        """,
                Map.of (
                        "limit", limit,
                        "offset", offset),
                BeanPropertyRowMapper.newInstance(UserGetAllResponseDTO.class)
        );
    }

    public UserRegisterResponseDTO register(UserRegisterRequestDTO requestDTO) {
        final String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());
        return template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO users(login, password, role)
                        VALUES (:login, :password, :role)
                        RETURNING id, login, role
                        """,
                Map.of (
                        "login", requestDTO.getLogin(),
                        "password", encodedPassword,
                        "role", Authentication.ROLE_USER),
                BeanPropertyRowMapper.newInstance(UserRegisterResponseDTO.class)
        );
    }

    public UserMeResponseDTO me() throws NotAuthenticatedException, PasswordNotMatchesException {
        Authentication authentication = authenticator.authenticate();
        return new UserMeResponseDTO(
                authentication.getId(),
                authentication.getLogin(),
                authentication.getRole()
        );
    }

    public UserRemoveByIdResponseDTO removeById(long id) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        final Authentication authentication = authenticator.authenticate();
        if (authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            return template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE users SET removed = TRUE WHERE id = :id
                            RETURNING id, login, role
                            """,
                    Map.of ("id", id),
                    BeanPropertyRowMapper.newInstance(UserRemoveByIdResponseDTO.class)
            );
        }
        if (authentication.getId() == id) {
            return template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE users SET removed = TRUE WHERE id = :id
                            RETURNING id, login, role
                            """,
                    Map.of ("id", id),
                    BeanPropertyRowMapper.newInstance(UserRemoveByIdResponseDTO.class)
            );
        }

        throw new ForbiddenException(); // код ошибки 403
    }

    public UserRestoreByIdResponseDTO restoreById(long id) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        final Authentication authentication = authenticator.authenticate();
        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        return template.queryForObject(
                // language=PostgreSQL
                """
                        UPDATE users SET removed = FALSE WHERE id = :id
                        RETURNING id, login, role
                        """,
                Map.of ("id", id),
                BeanPropertyRowMapper.newInstance(UserRestoreByIdResponseDTO.class)
        );
    }
}


