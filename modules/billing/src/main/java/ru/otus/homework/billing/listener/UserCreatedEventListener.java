package ru.otus.homework.billing.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ru.otus.homework.billing.config.RabbitConfig;
import ru.otus.homework.billing.event.AccountCreated;
import ru.otus.homework.billing.event.UserCreatedEvent;
import ru.otus.homework.billing.service.AccountService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedEventListener {

    private final AccountService accountService;

    @RabbitListener(queues = RabbitConfig.ACCOUNT_QUEUE)
    public AccountCreated process(Message<UserCreatedEvent> msg) {
        log.info("Receive User created: {}", msg.getPayload().getUserId());
        return new AccountCreated(accountService.create(msg.getPayload().getUserId()).userId());
    }

}
