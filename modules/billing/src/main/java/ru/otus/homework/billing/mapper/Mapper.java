package ru.otus.homework.billing.mapper;

public interface Mapper<F, T> {

    T map(F object);

}
