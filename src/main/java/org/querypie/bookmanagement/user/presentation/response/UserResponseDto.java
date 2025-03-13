package org.querypie.bookmanagement.user.presentation.response;

import org.querypie.bookmanagement.user.domain.User;

public record UserResponseDto(
    Long id,
    String name,
    String email
) {
    public static UserResponseDto of(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }
}
