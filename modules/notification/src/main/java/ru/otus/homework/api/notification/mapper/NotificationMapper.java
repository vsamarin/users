package ru.otus.homework.api.notification.mapper;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.otus.homework.api.notification.dto.Notification;
import ru.otus.homework.api.notification.entity.NotificationEntity;
import ru.otus.homework.api.notification.event.NotificationEvent;

import java.util.Collections;
import java.util.List;

@Component
public class NotificationMapper implements Mapper<NotificationEntity, Notification> {

    @Override
    public Notification map(NotificationEntity entity) {
        if (entity == null) return null;
        Notification notification = new Notification();
        notification.setId(entity.id());
        notification.setUserId(entity.userId());
        notification.setMessage(entity.message());
        return notification;
    }

    public NotificationEntity map(NotificationEvent event) {
        if (event == null) return null;
        NotificationEntity entity = new NotificationEntity();
        entity.userId(event.getUserId());
        entity.message(event.getMessage());
        return entity;
    }

    public List<Notification> map(List<NotificationEntity> entities) {
        return CollectionUtils.isEmpty(entities) ?
                Collections.emptyList() :
                entities.stream().map(this::map).toList();
    }

}
