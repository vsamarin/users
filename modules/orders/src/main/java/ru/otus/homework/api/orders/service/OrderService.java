package ru.otus.homework.api.orders.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.otus.homework.api.orders.config.RabbitConfig;
import ru.otus.homework.api.orders.entity.OrderEntity;
import ru.otus.homework.api.orders.event.NotificationEvent;
import ru.otus.homework.api.orders.event.WithdrawEvent;
import ru.otus.homework.api.orders.event.WithdrawResultEvent;
import ru.otus.homework.api.orders.exception.NotFoundException;
import ru.otus.homework.api.orders.repository.OrderRepository;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepository;

    public OrderEntity create(OrderEntity entity) {
        WithdrawResultEvent reply = rabbitTemplate.convertSendAndReceiveAsType(
                RabbitConfig.WITHDRAW_EXCHANGE,
                RabbitConfig.WITHDRAW_QUEUE,
                new WithdrawEvent(entity.userId(), entity.price()),
                new CorrelationData(String.valueOf(entity.userId())),
                new ParameterizedTypeReference<>() {
                }
        );
        if (reply != null && reply.getStatus().equals(WithdrawResultEvent.Status.OK)) {
            OrderEntity order = orderRepository.save(entity.id(null));
            NotificationEvent event = new NotificationEvent();
            event.setUserId(order.userId());
            event.setMessage(format("Товар успешно приобретен: %s.", order.product()));
            rabbitTemplate.convertAndSend(RabbitConfig.NOTIFICATION_EXCHANGE, RabbitConfig.NOTIFICATION_QUEUE, event);
            return order;
        } else {
            NotificationEvent event = new NotificationEvent();
            event.setUserId(entity.userId());
            event.setMessage(format("Мы сожалеем не удалось приобрести товар: %s.", entity.product()));
            rabbitTemplate.convertAndSend(RabbitConfig.NOTIFICATION_EXCHANGE, RabbitConfig.NOTIFICATION_QUEUE, event);
            throw new IllegalStateException(
                    format("Unable to create order %s account doesn't have enough balance", entity.userId())
            );
        }
    }

    public OrderEntity findById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(format("Order %s not found.", id)));
    }

}
