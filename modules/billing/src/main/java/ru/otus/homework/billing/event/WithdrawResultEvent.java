package ru.otus.homework.billing.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawResultEvent {

    private Status status;

    public enum Status {
        OK,
        ERROR,
    }

}
