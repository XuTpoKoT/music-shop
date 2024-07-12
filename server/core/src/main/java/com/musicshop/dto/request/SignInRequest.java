package com.musicshop.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(@NotBlank(message = "Имя пользователя не может быть пустым")
                            String login,
                            @NotBlank(message = "Пароль не может быть пустым")
                            String password) {
}
