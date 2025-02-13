package org.querypie.bookmanagement.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.querypie.bookmanagement.book.service.BookService;
import org.querypie.bookmanagement.example.service.ExampleService;
import org.querypie.bookmanagement.rental.service.RentalService;
import org.querypie.bookmanagement.user.service.UserService;
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
    protected BookService bookService;

    @MockitoBean
    protected UserService userService;

    @MockitoBean
    protected RentalService rentalService;
}
