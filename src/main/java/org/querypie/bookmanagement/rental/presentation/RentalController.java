package org.querypie.bookmanagement.rental.presentation;

import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.common.support.response.ApiResponse;
import org.querypie.bookmanagement.rental.presentation.request.RentalBookRequestDto;
import org.querypie.bookmanagement.rental.presentation.request.ReturnBookRequestDto;
import org.querypie.bookmanagement.rental.presentation.response.RentalAvailableResponse;
import org.querypie.bookmanagement.rental.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/api/v1/rentals")
@RestController
public class RentalController {

    private final RentalService rentalService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<?> rentalBooks(
        @RequestBody RentalBookRequestDto request
    ) {
        LocalDateTime rentalAt = LocalDateTime.now();
        rentalService.rentalBooks(request.toCommand(), rentalAt);
        return ApiResponse.success();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{rentalId}/books/return")
    public ApiResponse<?> returnBooks(
        @PathVariable Long rentalId,
        @RequestBody ReturnBookRequestDto request
    ) {
        LocalDateTime returnAt = LocalDateTime.now();
        rentalService.returnBooks(request.toCommand(rentalId), returnAt);
        return ApiResponse.success();
    }

    @GetMapping("/books/{bookId}/rental-available")
    public ApiResponse<RentalAvailableResponse> getRentalAvailable(
        @PathVariable Long bookId
    ) {
        boolean rentalAvailable = rentalService.isRentalAvailable(bookId);
        return ApiResponse.success(new RentalAvailableResponse(rentalAvailable));
    }
}
