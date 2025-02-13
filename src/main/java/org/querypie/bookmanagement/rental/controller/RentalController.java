package org.querypie.bookmanagement.rental.controller;

import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.common.support.response.ApiResponse;
import org.querypie.bookmanagement.rental.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/api/v1/rental")
@RestController
public class RentalController {

    private final RentalService rentalService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<?> rental(
        @RequestBody RentalBookRequestDto request
    ) {
        LocalDateTime rentalAt = LocalDateTime.now();
        rentalService.rentalBook(request.toCommand(), rentalAt);
        return ApiResponse.success();
    }
}
