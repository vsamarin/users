package ru.otus.homework.users.mapper;

import org.springframework.stereotype.Service;
import ru.otus.homework.users.dto.User;
import ru.otus.homework.users.entity.UserEntity;

@Service
public class UserMapper implements Mapper<UserEntity, User> {

    @Override
    public User map(UserEntity entity) {
        if (entity == null) return null;
        User user = new User();
        user.setId(entity.id());
        user.setUsername(entity.username());
        user.setFirstName(entity.firstName());
        user.setLastName(entity.lastName());
        user.setEmail(entity.email());
        user.setPhone(entity.phone());
        return user;
    }

    public UserEntity map(User dto) {
        return dto == null ?
                null :
                new UserEntity()
                        .id(dto.getId())
                        .username(dto.getUsername())
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .email(dto.getEmail())
                        .phone(dto.getPhone());
    }

    public UserEntity map(Long id, User dto) {
        return dto == null ?
                null :
                new UserEntity()
                        .id(id)
                        .username(dto.getUsername())
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .email(dto.getEmail())
                        .phone(dto.getPhone());
    }

}
