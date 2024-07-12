package com.musicshop.controller;

import com.musicshop.entity.PickUpPoint;
import com.musicshop.error.ErrorDescription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "pickup-points")
@RequestMapping("/${api-version}/pickup-points")
public interface PickUpPointController {
    @Operation(summary = "Получение точек доставки")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PickUpPoint.class)))}),
            @ApiResponse(
                    responseCode = "401",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))}),
            @ApiResponse(
                    responseCode = "503",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDescription.class))})
    })
    @GetMapping()
    List<PickUpPoint> getAllPickUpPoints();
}
