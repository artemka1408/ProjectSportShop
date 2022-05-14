package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ReviewRemoveByIdResponseDTO {
    private long id;
    private long userId;
    private long productId;
    private String comment;

}
