package org.querypie.bookmanagement.example.controller.dto.request;

import org.querypie.bookmanagement.example.domain.ExampleData;

public record ExampleRequestDto(
    String data
) {
    public ExampleData toExampleData() {
        return new ExampleData(data, data);
    }
}
