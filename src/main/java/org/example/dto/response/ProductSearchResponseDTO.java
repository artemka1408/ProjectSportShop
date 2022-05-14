package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductSearchResponseDTO {
    private long id;
    private String category;
    private String name;
    private int price;
    private String photo;
}
