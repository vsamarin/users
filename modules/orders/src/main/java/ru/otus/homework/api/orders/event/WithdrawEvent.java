package ru.otus.homework.api.orders.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawEvent {
    private Long userId;
    private Long amount;
}
