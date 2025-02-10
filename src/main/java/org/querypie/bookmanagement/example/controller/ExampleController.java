package org.querypie.bookmanagement.example.controller;

import org.querypie.bookmanagement.common.support.response.ApiResponse;
import org.querypie.bookmanagement.example.controller.dto.request.ExampleRequestDto;
import org.querypie.bookmanagement.example.controller.dto.response.ExampleResponseDto;
import org.querypie.bookmanagement.example.domain.ExampleData;
import org.querypie.bookmanagement.example.domain.ExampleResult;
import org.querypie.bookmanagement.example.service.ExampleService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExampleController {

    private final ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("/get/{exampleValue}")
    public ApiResponse<ExampleResponseDto> exampleGet(@PathVariable String exampleValue,
                                                      @RequestParam String exampleParam) {
        ExampleResult result = exampleService.processExample(new ExampleData(exampleValue, exampleParam));
        return ApiResponse.success(new ExampleResponseDto(result.data()));
    }

    @PostMapping("/post")
    public ApiResponse<ExampleResponseDto> examplePost(@RequestBody ExampleRequestDto request) {
        ExampleResult result = exampleService.processExample(request.toExampleData());
        return ApiResponse.success(new ExampleResponseDto(result.data()));
    }

}
