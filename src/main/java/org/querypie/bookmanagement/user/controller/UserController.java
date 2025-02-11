package org.querypie.bookmanagement.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.common.support.response.ApiResponse;
import org.querypie.bookmanagement.user.controller.request.UserRegisterRequest;
import org.querypie.bookmanagement.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<?> registerUser(
        @Valid @RequestBody UserRegisterRequest request
    ) {
        userService.register(request.toCommand());
        return ApiResponse.success();
    }
}
