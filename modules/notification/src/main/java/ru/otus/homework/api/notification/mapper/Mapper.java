package ru.otus.homework.api.notification.mapper;

public interface Mapper<F, T> {

    T map(F object);

}
