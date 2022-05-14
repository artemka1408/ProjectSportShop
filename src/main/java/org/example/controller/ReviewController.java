package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.response.ReviewGetAllResponseDTO;
import org.example.dto.response.ReviewMakeResponseDTO;
import org.example.dto.response.ReviewRemoveByIdResponseDTO;
import org.example.exception.*;
import org.example.manager.ReviewManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReviewController {
    private ReviewManager manager;

    @RequestMapping("/reviews.makeReview")
    public ReviewMakeResponseDTO make(long productId, String comment) throws NotAuthenticatedException, NotMakeReviewException, PasswordNotMatchesException {
        return manager.makeReview(productId, comment);
    }

    @RequestMapping("/reviews.getAll")
    public List<ReviewGetAllResponseDTO> getAll(int limit, int offset) throws InvalidLimitException, ForbiddenException, NotAuthenticatedException, InvalidOffsetException, PasswordNotMatchesException {
        return manager.getAll(limit, offset);
    }

    @RequestMapping("/reviews.removeById")
    public ReviewRemoveByIdResponseDTO removeById(long id) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        return manager.removeById(id);
    }
}


