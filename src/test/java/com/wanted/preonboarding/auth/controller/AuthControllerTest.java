package com.wanted.preonboarding.auth.controller;

import com.wanted.preonboarding.auth.AuthTokenProvider;
import com.wanted.preonboarding.auth.config.AuthConstants;
import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.auth.dto.CreateAuthToken;
import com.wanted.preonboarding.auth.service.AuthTokenGenerateService;
import com.wanted.preonboarding.auth.service.TokenFetchService;
import com.wanted.preonboarding.auth.service.TokenQueryService;
import com.wanted.preonboarding.auth.validator.PasswordCheckService;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.document.utils.RestDocsTestSupport;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.service.UserFindService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.wanted.preonboarding.document.utils.RestDocsConfig.field;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends RestDocsTestSupport {



    @Test
    @DisplayName("Access Token 발급")
    void getAccessToken() throws Exception {

        // given
        CreateAuthToken createAuthToken = AuthModuleHelper.toCreateAuthToken();
        AuthToken authToken = AuthModuleHelper.toJwtAuthToken_another_constructor();
        BaseUserInfo baseUserInfo = AuthModuleHelper.toBaseUserInfo();

        when(authTokenGenerateService.generateToken(anyString())).thenReturn(authToken);
        when(userFindService.fetchUserInfo(createAuthToken.getEmail())).thenReturn(baseUserInfo);

        // when & then
        mockMvc.perform(post("/api/v1/auth/authentication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAuthToken)))
                .andExpect(status().isOk())
                .andExpect(header().string(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + authToken.getToken()))
                .andDo(document("auth/get-access-token",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일 주소")
                                        .attributes(field("constraints", "이메일 형식 xxx@xxx")),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                        .attributes(field("constraints", "최대 20자"))
                        ),
                        responseFields(dataFields())
                ));
    }

}