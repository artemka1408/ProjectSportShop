package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PurchaseGetAllResponseDTO {
        private long id;
        private long productId;
        private String productCategory;
        private String productName;
        private int productPrice;
        private int qty;
        private long userId;
        private String userLogin;

    }
