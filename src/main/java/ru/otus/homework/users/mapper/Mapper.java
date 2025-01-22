package ru.otus.homework.users.mapper;

public interface Mapper<F, T> {

    T map(F object);

}
