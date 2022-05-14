package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ReviewMakeResponseDTO {
    private long id;
    private long userId;
    private long productId;
    private String comment;

}
