package org.querypie.bookmanagement.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.querypie.bookmanagement.book.presentation.port.BookCommandService;
import org.querypie.bookmanagement.book.presentation.port.BookQueryService;
import org.querypie.bookmanagement.example.service.ExampleService;
import org.querypie.bookmanagement.rental.presentation.port.RentalService;
import org.querypie.bookmanagement.user.presentation.port.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected ExampleService exampleService;

    @MockitoBean
    protected BookQueryService bookQueryService;

    @MockitoBean
    protected BookCommandService bookCommandService;

    @MockitoBean
    protected UserService userService;

    @MockitoBean
    protected RentalService rentalService;
}
