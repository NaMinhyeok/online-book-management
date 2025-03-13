package org.querypie.bookmanagement.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.common.support.response.ApiResponse;
import org.querypie.bookmanagement.user.presentation.request.UserRegisterRequest;
import org.querypie.bookmanagement.user.presentation.response.AllUserResponseDto;
import org.querypie.bookmanagement.user.presentation.response.UserResponseDto;
import org.querypie.bookmanagement.user.domain.User;
import org.querypie.bookmanagement.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ApiResponse<AllUserResponseDto> getUsers() {
        List<User> users = userService.getUsers();
        return ApiResponse.success(new AllUserResponseDto(users.stream().map(UserResponseDto::of).toList()));
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto> getUser(
        @PathVariable Long userId
    ) {
        User user = userService.getUser(userId);
        return ApiResponse.success(UserResponseDto.of(user));
    }
}
