package org.example.manager;

import lombok.AllArgsConstructor;
import org.example.authentication.Authentication;
import org.example.authenticator.Authenticator;
import org.example.dto.response.*;
import org.example.dto.request.ProductCreateRequestDTO;
import org.example.dto.request.ProductUpdateRequestDTO;
import org.example.exception.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ProductManager {
    private NamedParameterJdbcTemplate template;
    private Authenticator authenticator;

    public List<ProductGetAllResponseDTO> getAll(int limit, int offset) throws NotAuthenticatedException, PasswordNotMatchesException, InvalidLimitException, InvalidOffsetException, ForbiddenException {

        if (limit > 50 || limit <= 0)
            throw new InvalidLimitException();

        if (offset < 0)
            throw new InvalidOffsetException();

        return template.query(
                //language=PostgreSQL
                """
                        SELECT id, category, name, price, photo FROM products
                        WHERE removed = FALSE 
                        ORDER BY id
                        LIMIT :limit OFFSET :offset
                               
                          """,
                Map.of(
                        "limit", limit,
                        "offset", offset
                ),
                BeanPropertyRowMapper.newInstance(ProductGetAllResponseDTO.class)
        );
    }

    public ProductGetByIdResponseDTO getById(long id) {
        return template.queryForObject(
                //language=PostgreSQL
                """
                        SELECT id, category, name, description, price, size, manufacturer, photo
                        FROM products
                        WHERE removed = FALSE AND id =:id
                        """,
                Map.of("id", id),
                BeanPropertyRowMapper.newInstance(ProductGetByIdResponseDTO.class)
        );
    }

    public ProductCreateResponseDTO create(ProductCreateRequestDTO requestDTO) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        Authentication authentication = authenticator.authenticate();

        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        return template.queryForObject(
                //language=PostgreSQL
                """
                        INSERT INTO products(category, name, description, price, size, manufacturer, photo)
                        VALUES (:category, :name, :description, :price, :size, :manufacturer, :photo)
                        RETURNING id, category, name, description, price, size, manufacturer, photo
                        """,
                Map.of(
                        "category", requestDTO.getCategory(),
                        "name", requestDTO.getName(),
                        "description", requestDTO.getDescription(),
                        "price", requestDTO.getPrice(),
                        "size", requestDTO.getSize(),
                        "manufacturer", requestDTO.getManufacturer(),
                        "photo", requestDTO.getPhoto()
                        ),
                BeanPropertyRowMapper.newInstance(ProductCreateResponseDTO.class)
        );
    }

    public ProductUpdateResponseDTO update(ProductUpdateRequestDTO requestDTO) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        Authentication authentication = authenticator.authenticate();

        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        return template.queryForObject(
                //language=PostgreSQL
                """
                        UPDATE products 
                        SET category = :category, name = :name, description = :description, price = :price, size = :size, manufacturer = :manufacturer, photo = :photo
                        WHERE removed = FALSE AND id = :id
                        RETURNING id, category, name, description, price, size, manufacturer, photo
                             """,
                Map.of(
                        "id", requestDTO.getId(),
                        "category", requestDTO.getCategory(),
                        "name", requestDTO.getName(),
                        "description", requestDTO.getDescription(),
                        "price", requestDTO.getPrice(),
                        "size", requestDTO.getSize(),
                        "manufacturer", requestDTO.getManufacturer(),
                        "photo", requestDTO.getPhoto()
                ),
                BeanPropertyRowMapper.newInstance(ProductUpdateResponseDTO.class)
        );
    }

    public ProductRemoveByIdResponseDTO removeById(long id) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        Authentication authentication = authenticator.authenticate();

        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        return template.queryForObject(
                //language=PostgreSQL
                """
                        UPDATE products SET removed = TRUE WHERE id = :id
                        RETURNING id, category, name, description, price, size, manufacturer, photo
                        """,
                Map.of(
                        "id", id),
                BeanPropertyRowMapper.newInstance(ProductRemoveByIdResponseDTO.class)
        );
    }


    public ProductRestoreByIdResponseDTO restoreById(long id) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        Authentication authentication = authenticator.authenticate();

        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        return template.queryForObject(
                //language=PostgreSQL
                """
                        UPDATE products SET removed = FALSE WHERE id = :id
                        RETURNING id, category, name, description, price, size, manufacturer, photo
                        """,
                Map.of(
                        "id", id),
                BeanPropertyRowMapper.newInstance(ProductRestoreByIdResponseDTO.class)
        );
    }

    public List<ProductGetAllRemovedResponseDTO> getAllRemoved(int limit, int offset) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        Authentication authentication = authenticator.authenticate();

        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        return template.query(
                //language=PostgreSQL
                """
                        SELECT id, category, name, description, price, size, manufacturer, photo FROM products
                        WHERE removed = TRUE
                        ORDER BY id
                        LIMIT :limit OFFSET :offset
                               
                          """,
                Map.of(
                        "limit", limit,
                        "offset", offset
                ),
                BeanPropertyRowMapper.newInstance(ProductGetAllRemovedResponseDTO.class)
        );
    }

    public List<ProductSearchResponseDTO> search(String query, String language, int limit, int offset) {
        return template.query(
                //language=PostgreSQL
                """
                        SELECT id, category, description, name, price, photo FROM products
                        WHERE removed = FALSE
                          AND to_tsvector(CAST(:language AS regconfig), category || ' ' || products.description) @@ to_tsquery(CAST(:language AS regconfig), :query)
                        ORDER BY id
                        LIMIT :limit OFFSET :offset
                        """,
                Map.of(
                        "query", query,
                        "language", language,
                        "limit", limit,
                        "offset", offset
                ),
                BeanPropertyRowMapper.newInstance(ProductSearchResponseDTO.class)
        );


    }
}



