package org.querypie.bookmanagement.user.presentation.response;

import java.util.List;

public record AllUserResponseDto(
    List<UserResponseDto> users
) {}
