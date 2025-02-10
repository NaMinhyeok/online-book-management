package org.querypie.bookmanagement.example.service;

import org.querypie.bookmanagement.example.domain.ExampleData;
import org.querypie.bookmanagement.example.domain.ExampleResult;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public ExampleResult processExample(ExampleData exampleData) {
        return new ExampleResult(exampleData.value());
    }
}
