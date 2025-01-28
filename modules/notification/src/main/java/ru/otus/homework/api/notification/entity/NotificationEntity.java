package ru.otus.homework.api.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Table(name = "t_notification")
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @SequenceGenerator(name = "t_notification_id_seq", sequenceName = "t_notification_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "t_notification_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "message", nullable = false)
    private String message;

}
