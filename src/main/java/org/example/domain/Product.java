package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class Product {
    private long id;
    private String category;
    private String name;
    private String description;
    private int price;
    private int size;
    private String madeIn;
    private String photo;
}
