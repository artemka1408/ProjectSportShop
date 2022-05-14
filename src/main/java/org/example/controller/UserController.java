package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.request.UserRegisterRequestDTO;
import org.example.dto.response.UserMeResponseDTO;
import org.example.dto.response.UserRegisterResponseDTO;
import org.example.dto.response.UserRemoveByIdResponseDTO;
import org.example.dto.response.UserRestoreByIdResponseDTO;
import org.example.exception.*;
import org.example.manager.UserManager;
import org.example.dto.response.UserGetAllResponseDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private UserManager manager;

    @RequestMapping("/users.getAll")
    public List<UserGetAllResponseDTO> getAll(int limit, long offset) throws InvalidLimitException, InvalidOffsetException, ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.getAll(limit, offset);
    }

    @RequestMapping("/users.register")
    public UserRegisterResponseDTO register(UserRegisterRequestDTO requestDTO) {
        return manager.register(requestDTO);
    }

    @RequestMapping("/users.me")
    public UserMeResponseDTO me() throws NotAuthenticatedException, PasswordNotMatchesException {
        return manager.me();
    }

    @RequestMapping("/users.removeById")
    public UserRemoveByIdResponseDTO removeById(long id) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.removeById(id);
    }

    @RequestMapping("/users.restoreById")
    public UserRestoreByIdResponseDTO restoreById(long id) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.restoreById(id);
    }
}


