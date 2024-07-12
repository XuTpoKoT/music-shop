package com.musicshop.controller;

import com.musicshop.dto.request.MakeOrderRequest;
import com.musicshop.dto.request.SetOrderStatusRequest;
import com.musicshop.dto.response.OrderPageResponse;
import com.musicshop.dto.response.OrderResponse;
import com.musicshop.error.ErrorDescription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "orders")
@RequestMapping("/${api-version}/orders")
public interface OrderController {
    @Operation(summary = "Получение заказа по ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(
                    responseCode = "401",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
            @ApiResponse(
                    responseCode = "503",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))})
    })
    @GetMapping("/{id}")
    OrderResponse getOrderById(@PathVariable UUID id);

    @Operation(summary = "Получение заказов по логину")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderPageResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
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
    @GetMapping()
    OrderPageResponse getOrdersByLogin(@RequestParam String login,
                                       @RequestParam(defaultValue = "1") @Min(1) int pageNumber,
                                       @RequestParam(defaultValue = "${defaultPageSize}") @Min(1) int pageSize);

    @Operation(summary = "Оформление заказа")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(
                    responseCode = "400",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
            @ApiResponse(
                    responseCode = "401",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
            @ApiResponse(
                    responseCode = "403",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
            @ApiResponse(
                    responseCode = "503",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))})
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    void makeOrder(@RequestParam String login, @RequestBody MakeOrderRequest makeOrderRequest);

    @Operation(summary = "Изменение статуса заказа")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(
                    responseCode = "400",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
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
    @PatchMapping("/{id}")
    void setOrderStatus(@PathVariable UUID id, @RequestBody SetOrderStatusRequest request);
}
