package com.wanted.preonboarding.module.queue.controller;

import com.wanted.preonboarding.auth.config.AuthConstants;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.RestDocsTestSupport;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueueControllerTest extends RestDocsTestSupport {



    @Test
    public void testEnqueue() throws Exception {

        Users buyer = UsersModuleHelper.toUsersWithId();
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo(buyer);
        when(userFindService.fetchUserInfo(anyString())).thenReturn(userInfo);
        securityUserMockSetting(buyer);

        when(userQueueService.getPosition(buyer.getEmail())).thenReturn(1);

        mockMvc.perform(post("/api/v1/enqueue")
                .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + token))
                .andExpect(status().isOk());

        verify(userQueueService, times(1)).getPosition(buyer.getEmail());
        verify(queueNotificationService, times(1)).notifyQueueStatus(buyer.getEmail(), 1);
    }

    @Test
    public void testGetPosition() throws Exception {
        Users buyer = UsersModuleHelper.toUsersWithId();
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo(buyer);
        when(userFindService.fetchUserInfo(anyString())).thenReturn(userInfo);
        securityUserMockSetting(buyer);


        when(userQueueService.getPosition(buyer.getEmail())).thenReturn(1);

        mockMvc.perform(get("/api/v1/queue/position")
                        .header(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.BEARER_PREFIX + token))
                .andExpect(status().isOk());

        verify(userQueueService, times(1)).getPosition(buyer.getEmail());
    }

    @Test
    public void testDequeue() throws Exception {
        Users buyer = UsersModuleHelper.toUsersWithId();
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo(buyer);
        when(userFindService.fetchUserInfo(anyString())).thenReturn(userInfo);
        securityUserMockSetting(buyer);


        when(userQueueService.pop(any())).thenReturn(Optional.of(buyer.getEmail()));

        mockMvc.perform(post("/api/v1/dequeue"))
                .andExpect(status().isOk());

        verify(userQueueService, times(1)).pop(any());
        verify(queueNotificationService, times(1)).notifyQueueStatus(buyer.getEmail(), 0);
    }



}