package org.example.preonboarding.common.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EntityDeleteEventListener implements PostDeleteEventListener {

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        Object entity = event.getEntity();
        log.info("Entity deleted: {}", entity.toString());
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return false;
    }
}