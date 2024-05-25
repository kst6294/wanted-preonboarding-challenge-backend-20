package com.wanted.preonboarding.document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wanted.preonboarding.document.utils.CustomResponseFieldsSnippet;
import com.wanted.preonboarding.document.utils.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;


import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = {CommonDocController.class}
)
public class CommonDocControllerTest extends RestDocsTestSupport {

    @Test
    public void enums() throws Exception {
        // 요청
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/test/enums")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // 결과값 출력
        MvcResult mvcResult = result.andReturn();


        // 데이터 파싱
        EnumDocs enumDocs = getData(mvcResult);

        // 문서화 진행
        result.andExpect(status().isOk())
                .andDo(restDocs.document(
                        customResponseFields("custom-response", beneathPath("data.redisKey").withSubsectionId("redisKey"),
                                attributes(key("title").value("redisKey")),
                                enumConvertFieldDescriptor((enumDocs.getRedisKey()))
                        ),
                        customResponseFields("custom-response", beneathPath("data.yn").withSubsectionId("yn"),
                                attributes(key("title").value("yn")),
                                enumConvertFieldDescriptor((enumDocs.getYn()))
                        ),
                        customResponseFields("custom-response", beneathPath("data.memberShip").withSubsectionId("memberShip"),
                                attributes(key("title").value("memberShip")),
                                enumConvertFieldDescriptor((enumDocs.getMemberShip()))
                        ),
                        customResponseFields("custom-response", beneathPath("data.jwtErrorEnum").withSubsectionId("jwtErrorEnum"),
                                attributes(key("title").value("jwtErrorEnum")),
                                enumConvertFieldDescriptor((enumDocs.getJwtErrorEnum()))
                        )
                ));
    }

    public static CustomResponseFieldsSnippet customResponseFields
            (String type,
             PayloadSubsectionExtractor<?> subsectionExtractor,
             Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes, true);
    }

    private static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {
        return enumValues.entrySet().stream()
                .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }

    private EnumDocs getData(MvcResult result) throws IOException {
        ApiResponseDto<EnumDocs> apiResponseDto = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(),
                        new TypeReference<ApiResponseDto<EnumDocs>>() {}
                );
        return apiResponseDto.getData();
    }
}
