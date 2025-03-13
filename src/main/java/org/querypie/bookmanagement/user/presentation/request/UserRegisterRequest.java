package org.querypie.bookmanagement.user.presentation.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.querypie.bookmanagement.user.service.command.UserRegisterCommand;

public record UserRegisterRequest(
    @NotBlank
    String name,
    @Email
    String email
) {
    public UserRegisterCommand toCommand() {
        return new UserRegisterCommand(name, email);
    }
}
