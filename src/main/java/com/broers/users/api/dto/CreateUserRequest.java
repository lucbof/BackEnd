package com.broers.users.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CreateUserRequest(
    @NotNull @NotEmpty String name, 
    @NotNull @Email String email, 
    @NotNull @NotEmpty String userName, 
    @NotNull @NotEmpty String password) {}
