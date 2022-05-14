package org.example.manager;

import lombok.AllArgsConstructor;
import org.example.authentication.Authentication;
import org.example.authenticator.Authenticator;
import org.example.dto.response.PurchaseGetAllResponseDTO;
import org.example.dto.response.PurchaseGetAllResponseDTO;
import org.example.dto.response.ProductGetByIdResponseDTO;
import org.example.dto.response.PurchaseMakeResponseDTO;
import org.example.dto.response.PurchaseStatsResponseDTO;
import org.example.exception.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class PurchaseManager {

    private NamedParameterJdbcTemplate template;
    private Authenticator authenticator;

    public PurchaseMakeResponseDTO make(long productId, int qty) throws NotAuthenticatedException, PasswordNotMatchesException {
        Authentication authentication = authenticator.authenticate();
        Long userId = authentication.getId();
        if (authentication.isAnonymous()) {
            throw new NotAuthenticatedException();
        }

        ProductGetByIdResponseDTO product = template.queryForObject(
                //language=PostgreSQL
                """
                        SELECT id, category, name, description, price FROM products
                        WHERE removed = FALSE AND id = :id
                        """,
                Map.of("id", productId),
                BeanPropertyRowMapper.newInstance(ProductGetByIdResponseDTO.class)
        );

        int bonus = product.getPrice() * qty * 2 / 100;

        template.update(
                //language=PostgreSQL
                """
                          INSERT INTO bonuses (user_id, bonus) 
                          VALUES (:user_id, :bonus)
                        """,
                Map.of(
                        "user_id", authentication.getId(),
                        "bonus", bonus
                )
        );

        return template.queryForObject(
                //language=PostgreSQL
                """
                        INSERT INTO purchases  (product_id, product_category, product_name, product_price, user_id, qty)
                        VALUES (:product_id, :product_category, :product_name, :product_price, :user_id, :qty )
                        RETURNING id, product_id, product_category, product_name, product_price, user_id, qty
                        """,
                Map.of("product_id", product.getId(),
                        "product_category", product.getCategory(),
                        "product_name", product.getName(),
                        "product_price", product.getPrice(),
                        "user_id", authentication.getId(),
                        "qty", qty
                ),
                BeanPropertyRowMapper.newInstance(PurchaseMakeResponseDTO.class)
        );
    }

    public List<PurchaseGetAllResponseDTO> getAll(int limit, int offset) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException, InvalidLimitException, InvalidOffsetException {
        Authentication authentication = authenticator.authenticate();
        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        if (limit > 50 || limit < 0) {
            throw new InvalidLimitException();
        }

        if (offset < 0) {
            throw new InvalidOffsetException();
        }

        return template.query(
                //language=PostgreSQL
                """
                        SELECT p.product_id, p.product_category, p.product_name, p.product_price, p.qty, p.user_id, u.login user_login FROM purchases p 
                        JOIN users u ON u.id = p.user_id
                        ORDER BY p.id
                        LIMIT :limit OFFSET :offset
                        """,
                Map.of(
                        "limit", limit,
                        "offset", offset
                ),
                BeanPropertyRowMapper.newInstance(PurchaseGetAllResponseDTO.class)
        );

    }


    public PurchaseStatsResponseDTO stats() throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        Authentication authentication = authenticator.authenticate();

        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }
        PurchaseStatsResponseDTO stats = template.queryForObject(
                //language=PostgreSQL
                """
                        SELECT SUM(qty * product_price) sum, 
                               MIN(qty * product_price) min, 
                               MAX(qty * product_price) max, 
                               AVG(qty * product_price) avg 
                        FROM purchases

                        """,
                Map.of(),
                BeanPropertyRowMapper.newInstance(PurchaseStatsResponseDTO.class)
        );
        return stats;
    }
}
