package ru.otus.homework.api.orders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Table(name = "t_order")
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @SequenceGenerator(name = "t_order_id_seq", sequenceName = "t_order_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "t_order_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "product", nullable = false)
    private String product;

}
