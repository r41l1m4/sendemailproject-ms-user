package dev.ironia.ms.user.controllers;

import dev.ironia.ms.user.dtos.UserRecordDto;
import dev.ironia.ms.user.models.UserModel;
import dev.ironia.ms.user.repositories.UserRepository;
import dev.ironia.ms.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserRecordDto userRecordDto) {

        var userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        userService.save(userModel)
                );
    }
}
