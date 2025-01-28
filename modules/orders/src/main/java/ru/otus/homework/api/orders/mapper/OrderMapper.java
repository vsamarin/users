package ru.otus.homework.api.orders.mapper;

import org.springframework.stereotype.Component;
import ru.otus.homework.api.orders.dto.Order;
import ru.otus.homework.api.orders.entity.OrderEntity;

@Component
public class OrderMapper implements Mapper<OrderEntity, Order> {

    @Override
    public Order map(OrderEntity entity) {
        if (entity == null) return null;
        Order order = new Order();
        order.setId(entity.id());
        order.setUserId(entity.userId());
        order.setPrice(entity.price());
        order.setProduct(entity.product());
        return order;
    }

    public OrderEntity map(Order dto) {
        if (dto == null) return null;
        OrderEntity entity = new OrderEntity();
        entity.id(dto.getId());
        entity.userId(dto.getUserId());
        entity.price(dto.getPrice());
        entity.product(dto.getProduct());
        return entity;
    }

}
