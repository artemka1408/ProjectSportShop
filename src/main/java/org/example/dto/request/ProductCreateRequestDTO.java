package org.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductCreateRequestDTO {
    private String category;
    private String name;
    private String description;
    private int price;
    private int size;
    private String manufacturer;
    private String photo;
}
