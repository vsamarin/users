package ru.otus.homework.api.orders.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private Long id;
    private Long userId;
    private Long price;
    private String product;
}
