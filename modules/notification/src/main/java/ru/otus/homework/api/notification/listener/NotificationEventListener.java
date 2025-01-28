package ru.otus.homework.api.notification.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ru.otus.homework.api.notification.config.RabbitConfig;
import ru.otus.homework.api.notification.event.NotificationEvent;
import ru.otus.homework.api.notification.mapper.NotificationMapper;
import ru.otus.homework.api.notification.service.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @RabbitListener(queues = RabbitConfig.NOTIFICATION_QUEUE)
    public void process(Message<NotificationEvent> msg) {
        log.info("Receive notification user message: {} {}",
                msg.getPayload().getUserId(),
                msg.getPayload().getMessage()
        );
        notificationService.create(notificationMapper.map(msg.getPayload()));
    }

}
