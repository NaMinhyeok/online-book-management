package org.querypie.bookmanagement.user.service.command;

public record UserRegisterCommand(
    String name,
    String email
) {
}
