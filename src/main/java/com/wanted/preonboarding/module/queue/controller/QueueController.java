package com.wanted.preonboarding.module.queue.controller;

import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.queue.service.QueueNotificationService;
import com.wanted.preonboarding.module.queue.service.UserQueueService;
import com.wanted.preonboarding.module.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class QueueController {

    private final UserQueueService userQueueService;
    private final QueueNotificationService queueNotificationService;

    @PreAuthorize("hasAnyAuthority('NORMAL')")
    @PostMapping("/enqueue")
    public void enqueue() {
        String email = SecurityUtils.currentUserEmail();
        userQueueService.push(RedisKey.PRODUCT_QUEUE, email);
        int position = userQueueService.getPosition(email);
        queueNotificationService.notifyQueueStatus(email, position);
    }

    @PostMapping("/dequeue")
    public void dequeue() {
        Optional<String> sessionIdOpt = userQueueService.pop(RedisKey.PRODUCT_QUEUE);
        sessionIdOpt.ifPresent(s -> queueNotificationService.notifyQueueStatus(s, 0));
    }

    @PreAuthorize("hasAnyAuthority('NORMAL')")
    @GetMapping("/queue/position")
    public int getPosition() {
        String email = SecurityUtils.currentUserEmail();
        return userQueueService.getPosition(email);
    }

}
