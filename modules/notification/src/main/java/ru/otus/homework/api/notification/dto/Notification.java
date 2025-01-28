package ru.otus.homework.api.notification.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notification {
    private Long id;
    private Long userId;
    private String message;
}
