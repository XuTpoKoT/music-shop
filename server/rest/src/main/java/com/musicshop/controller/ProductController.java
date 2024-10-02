package com.musicshop.controller;

import com.musicshop.dto.response.ProductPageResponse;
import com.musicshop.dto.response.ProductResponse;
import com.musicshop.error.ErrorDescription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "products")
@RequestMapping("/${api-version}/products")
public interface ProductController {

    @Operation(summary = "Получение информации о товарах")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductPageResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
            @ApiResponse(
                    responseCode = "503",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))})
    })
    @GetMapping()
    ProductPageResponse getProducts(
            @RequestParam(name = "pageNumber", defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "${defaultPageSize}",
                    required = false) @Min(1) int pageSize,
            @RequestParam(name = "categoryId", required = false) UUID categoryId,
            @RequestParam(name = "productPrefix", required = false) String productPrefix);

    @Operation(summary = "Получение информации о товаре")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))}),
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
    ProductResponse getProductInfo(@PathVariable UUID id);
}
