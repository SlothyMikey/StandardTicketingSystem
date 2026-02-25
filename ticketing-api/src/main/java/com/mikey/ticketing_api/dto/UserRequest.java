package com.mikey.ticketing_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String full_name,
        @NotBlank String password,
        @NotBlank @Email String email
) {
}
