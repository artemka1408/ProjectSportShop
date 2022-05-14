package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseMakeResponseDTO {
    private long id;
    private long productId;
    private String productCategory;
    private String productName;
    private int productPrice;
    private int qty;
    private long userId;

}