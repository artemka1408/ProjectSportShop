package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductRestoreByIdResponseDTO {
    private long id;
    private String name;
    private String description;
    private int price;
    private int size;
    private String manufacturer;
    private String photo;
}
