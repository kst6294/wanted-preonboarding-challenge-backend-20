package com.wanted.preonboarding.module.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QueueController {

    private final QueueNotificationService queueNotificationService;

}
