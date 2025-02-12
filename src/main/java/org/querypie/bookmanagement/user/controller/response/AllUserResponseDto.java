package org.querypie.bookmanagement.user.controller.response;

import java.util.List;

public record AllUserResponseDto(
    List<UserResponseDto> users
) {}
