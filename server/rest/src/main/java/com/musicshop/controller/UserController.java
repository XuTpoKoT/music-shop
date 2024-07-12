package com.musicshop.controller;

import com.musicshop.dto.response.UserInfoResponse;
import com.musicshop.error.ErrorDescription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "users")
@RequestMapping("/${api-version}/users")
public interface UserController {

    @Operation(summary = "Получение информации о пользователе")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoResponse.class))}),
            @ApiResponse(
                    responseCode = "401",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
            @ApiResponse(
                    responseCode = "403",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
            @ApiResponse(
                    responseCode = "503",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))})
    })
    @GetMapping("/{login}")
    UserInfoResponse getUserInfo(@PathVariable String login);
}
