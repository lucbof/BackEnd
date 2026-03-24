package com.broers.users.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

public record LoginUserRequest(
    @NotNull @NotEmpty String userName, 
    @NotNull @NotEmpty String password) {} 
