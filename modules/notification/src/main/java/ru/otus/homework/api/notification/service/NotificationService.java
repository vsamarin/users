package ru.otus.homework.api.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.api.notification.entity.NotificationEntity;
import ru.otus.homework.api.notification.repository.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationEntity> search() {
        return notificationRepository.findAll();
    }

    public NotificationEntity create(NotificationEntity entity) {
        return notificationRepository.save(entity);
    }

}
