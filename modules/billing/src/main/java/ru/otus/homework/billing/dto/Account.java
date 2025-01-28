package ru.otus.homework.billing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private Long userId;
    private Long balance;
}
