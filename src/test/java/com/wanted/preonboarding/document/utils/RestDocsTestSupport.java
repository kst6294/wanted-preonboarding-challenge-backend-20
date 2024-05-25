package com.wanted.preonboarding.document.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.auth.controller.AuthController;
import com.wanted.preonboarding.infra.config.redis.TestContainersRedisConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.templates.TemplateResourceResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;


@Disabled
@ExtendWith(RestDocumentationExtension.class)
@Import(RestDocsConfig.class)
public abstract class RestDocsTestSupport extends ControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @Autowired
    ResourceLoader resourceLoader;

    protected List<FieldDescriptor> dataFields() {
        return List.of(
                fieldWithPath("data").type(JsonFieldType.NULL).description("null (응답 데이터가 없을 경우)"),
                fieldWithPath("response.status").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                fieldWithPath("response.message").type(JsonFieldType.STRING).description("응답 메세지")
        );
    }


    protected List<FieldDescriptor> statusMsg() {
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        fieldDescriptors.add(fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"));
        fieldDescriptors.add(fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"));
        return fieldDescriptors;
    }

    protected List<FieldDescriptor> errorStatusMsg() {
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        fieldDescriptors.add(fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"));
        fieldDescriptors.add(fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"));
        fieldDescriptors.add(fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 메세지"));
        fieldDescriptors.add(fieldWithPath("error").type(JsonFieldType.STRING).description("에러 코드"));

        return fieldDescriptors;
    }

    @BeforeEach
    void setUp(final WebApplicationContext context, final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider)
                        .operationPreprocessors()
                        .withResponseDefaults(Preprocessors.prettyPrint()))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }


}
