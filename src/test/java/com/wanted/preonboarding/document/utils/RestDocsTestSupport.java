package com.wanted.preonboarding.document.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.auth.AuthTokenProvider;
import com.wanted.preonboarding.auth.config.SecurityConfig;
import com.wanted.preonboarding.auth.handler.JwtAccessDeniedHandler;
import com.wanted.preonboarding.auth.handler.JwtAuthorizationDeniedHandler;
import com.wanted.preonboarding.infra.config.jwt.JwtConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Disabled
@ExtendWith(RestDocumentationExtension.class)
@Import({

        RestDocsConfig.class,
        SecurityConfig.class,
        JwtConfig.class,
        AuthTokenProvider.class,
        JwtAuthorizationDeniedHandler.class,
        JwtAccessDeniedHandler.class

})
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
        return List.of(
                fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
        );
    }

    protected List<FieldDescriptor> errorStatusMsg() {
        return List.of(
                fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 메세지"),
                fieldWithPath("error").type(JsonFieldType.STRING).description("에러 코드")
        );
    }

    protected List<FieldDescriptor> sliceDescription() {
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        fieldDescriptors.add(fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지인지의 여부"));
        fieldDescriptors.add(fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫 페이지인지의 여부"));
        fieldDescriptors.add(fieldWithPath("number").type(JsonFieldType.NUMBER).description("페이지"));
        fieldDescriptors.add(fieldWithPath("sort").type(JsonFieldType.OBJECT).description("sort"));
        fieldDescriptors.add(fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 조건이 아무것도 없는지의 여부"));
        fieldDescriptors.add(fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부"));
        fieldDescriptors.add(fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 안하는지 여부"));
        fieldDescriptors.add(fieldWithPath("size").type(JsonFieldType.NUMBER).description("한 페이지의 크기"));
        fieldDescriptors.add(fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("이 페이지에서 검색된 크기(size와 다른 개념)"));
        fieldDescriptors.add(fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("조회가 아무것도 되지 않았는지 여부"));
        fieldDescriptors.add(fieldWithPath("lastDomainId").optional().type(JsonFieldType.NUMBER).description("커서 기반을 위한 기준 아이디"));
        fieldDescriptors.add(fieldWithPath("cursorValue").optional().type(JsonFieldType.STRING).description("커서 기반을 위한 기준 값"));

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
                .apply(springSecurity())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }


}
