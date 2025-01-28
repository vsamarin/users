package ru.otus.homework.billing.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ru.otus.homework.billing.config.RabbitConfig;
import ru.otus.homework.billing.event.WithdrawEvent;
import ru.otus.homework.billing.event.WithdrawResultEvent;
import ru.otus.homework.billing.service.AccountService;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawEventListener {

    private final AccountService accountService;

    @RabbitListener(queues = RabbitConfig.WITHDRAW_QUEUE)
    public WithdrawResultEvent process(Message<WithdrawEvent> msg) {
        log.info("Receive withdraw user balance: {} {}", msg.getPayload().getUserId(), msg.getPayload().getAmount());
        try {
            accountService.withdraw(msg.getPayload().getUserId(), msg.getPayload().getAmount());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new WithdrawResultEvent(WithdrawResultEvent.Status.ERROR);
        }
        return new WithdrawResultEvent(WithdrawResultEvent.Status.OK);
    }

}
