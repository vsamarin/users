package ru.otus.homework.api.orders.mapper;

public interface Mapper<F, T> {

    T map(F object);

}
