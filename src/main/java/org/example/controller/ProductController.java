package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.response.*;
import org.example.dto.request.ProductCreateRequestDTO;
import org.example.dto.request.ProductUpdateRequestDTO;
import org.example.exception.*;
import org.example.manager.ProductManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {
    private ProductManager manager;

    @RequestMapping("/products.getAll")
    public List<ProductGetAllResponseDTO> getAll(int limit, int offset) throws InvalidLimitException, NotAuthenticatedException, InvalidOffsetException, PasswordNotMatchesException, ForbiddenException {
        return manager.getAll(limit, offset);
    }

    @RequestMapping("/products.getById")
    public ProductGetByIdResponseDTO getById(long id) {
        return manager.getById(id);
    }

    @RequestMapping("/products.create")
    public ProductCreateResponseDTO create(ProductCreateRequestDTO requestDTO) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.create(requestDTO);
    }

    @RequestMapping("/products.update")
    public ProductUpdateResponseDTO update(ProductUpdateRequestDTO requestDTO) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.update(requestDTO);
    }

    @RequestMapping("/products.removeById")
    public ProductRemoveByIdResponseDTO removeById(long id) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.removeById(id);
    }

    @RequestMapping("/products.restoreById")
    public ProductRestoreByIdResponseDTO restoreById(long id) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.restoreById(id);
    }

    @RequestMapping("/products.getAllRemoved")
    public List<ProductGetAllRemovedResponseDTO> getAllRemoved(int limit, int offset) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.getAllRemoved(limit, offset);
    }

    @RequestMapping("/products.search")
    public List<ProductSearchResponseDTO> search(String query, String language, int limit, int offset) {
        return manager.search(query, language, limit, offset);
    }
}
