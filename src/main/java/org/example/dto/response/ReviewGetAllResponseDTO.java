package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewGetAllResponseDTO {
   private long id;
   private long userId;
   private long productId;
   private String comment;
}
