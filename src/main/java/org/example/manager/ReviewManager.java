package org.example.manager;

import lombok.AllArgsConstructor;
import org.example.authentication.Authentication;
import org.example.authenticator.Authenticator;
import org.example.dto.response.ReviewGetAllResponseDTO;
import org.example.dto.response.ReviewMakeResponseDTO;
import org.example.dto.response.ReviewRemoveByIdResponseDTO;
import org.example.exception.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ReviewManager {
    private NamedParameterJdbcTemplate template;
    private Authenticator authenticator;

    public ReviewMakeResponseDTO makeReview(long productId, String comment) throws NotAuthenticatedException, PasswordNotMatchesException, NotMakeReviewException {
        Authentication authentication = authenticator.authenticate();
        if (authentication.getRole().equals(Authentication.ROLE_ANONYMOUS)) {
            throw new NotMakeReviewException();
        }

        return template.queryForObject(
                //language=PostgreSQL
                """
                        INSERT INTO reviews (user_id, product_id, comment)
                        VALUES (:user_id, :product_id, :comment)
                        RETURNING id, user_id, product_id, comment
                            """,
                Map.of(
                        "user_id", authentication.getId(),
                        "product_id", productId,
                        "comment", comment
                ),
                BeanPropertyRowMapper.newInstance(ReviewMakeResponseDTO.class)
        );
    }

    public List<ReviewGetAllResponseDTO> getAll(int limit, int offset) throws InvalidLimitException, InvalidOffsetException, NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        if (limit > 40 || limit < 0) {
            throw new InvalidLimitException();
        }

        if (offset < 0) {
            throw new InvalidOffsetException();
        }

        return template.query(
                //language=PostgreSQL
                """
                        SELECT user_id, product_id, comment
                        FROM reviews
                        ORDER BY id
                        LIMIT :limit OFFSET :offset
                        """,
                Map.of(
                        "limit", limit,
                        "offset", offset
                ),
                BeanPropertyRowMapper.newInstance(ReviewGetAllResponseDTO.class)
        );

    }

    public ReviewRemoveByIdResponseDTO removeById(long id) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        Authentication authentication = authenticator.authenticate();
        if (authentication.isAnonymous()) {
            throw new ForbiddenException();
        }

        return template.queryForObject(
                //language=PostgreSQL
                """
                        DELETE FROM reviews
                        WHERE id = :id
                        RETURNING id, user_id, product_id, comment
                        """,
                Map.of("id", id),
                BeanPropertyRowMapper.newInstance(ReviewRemoveByIdResponseDTO.class)

        );
    }
}


