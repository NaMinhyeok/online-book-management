package org.querypie.bookmanagement;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.example.controller.dto.request.ExampleRequestDto;
import org.querypie.bookmanagement.example.domain.ExampleResult;
import org.querypie.bookmanagement.support.ControllerTestSupport;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExampleControllerTest extends ControllerTestSupport {

    @Test
    void exampleGet() throws Exception {
        given(exampleService.processExample(any())).willReturn(new ExampleResult("BYE"));

        mockMvc.perform(get("/get/{exampleValue}", "HELLO_PATH")
                .queryParam("exampleParam", "HELLO_PARAM"))
            .andExpect(status().isOk())
            .andDo(document("exampleGet",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("Get example")
                        .tag("example")
                        .pathParameters(
                            parameterWithName("exampleValue")
                                .type(SimpleType.STRING)
                                .description("ExampleValue")
                        )
                        .queryParameters(
                            parameterWithName("exampleParam")
                                .type(SimpleType.STRING)
                                .description("ExampleParam")
                        )
                        .responseFields(
                            fieldWithPath("result").description("ResultType"),
                            fieldWithPath("data.result").description("Result Date"),
                            fieldWithPath("error").ignored()
                        )
                        .responseSchema(Schema.schema("exampleGetResponse"))
                        .build()
                ))
            );

    }

    @Test
    void examplePost() throws Exception {
        given(exampleService.processExample(any())).willReturn(new ExampleResult("BYE"));

        mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ExampleRequestDto("HELLO_DATA")))
            )
            .andExpect(status().isOk())
            .andDo(document("examplePost",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("Post example")
                        .tag("example")
                        .requestFields(
                            fieldWithPath("data").description("ExampleData")
                        )
                        .responseFields(
                            fieldWithPath("result").description("ResultType"),
                            fieldWithPath("data.result").description("Result Date"),
                            fieldWithPath("error").ignored()
                        )
                        .responseSchema(Schema.schema("examplePostResponse"))
                        .build()
                ))
            );

    }

}
