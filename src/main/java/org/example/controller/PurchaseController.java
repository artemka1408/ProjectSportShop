package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.response.PurchaseGetAllResponseDTO;
import org.example.dto.response.PurchaseMakeResponseDTO;
import org.example.dto.response.PurchaseStatsResponseDTO;
import org.example.exception.*;
import org.example.manager.PurchaseManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class
PurchaseController {
    private PurchaseManager manager;

    @RequestMapping("/purchases.make")
    public PurchaseMakeResponseDTO make(long productId, int qty) throws NotAuthenticatedException, PasswordNotMatchesException {
        return manager.make(productId, qty);
    }

    @RequestMapping("/purchases.getAll")
    public List<PurchaseGetAllResponseDTO> getAll(int limit, int offset) throws InvalidLimitException, ForbiddenException, NotAuthenticatedException, InvalidOffsetException, PasswordNotMatchesException {
        return manager.getAll(limit, offset);
    }

    @RequestMapping("/purchases.stats")
    public PurchaseStatsResponseDTO stats() throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException, ForbiddenException {
        return manager.stats();
    }
}
