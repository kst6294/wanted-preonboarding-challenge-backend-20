package com.wanted.preonboarding.module.queue;

import com.wanted.preonboarding.infra.config.websocket.QueueStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyQueueStatus(String sessionId, int position) {
        messagingTemplate.convertAndSend("/topic/queueStatus", new QueueStatus(sessionId, position));
    }


}
