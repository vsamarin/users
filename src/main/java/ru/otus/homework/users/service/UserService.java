package ru.otus.homework.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.users.entity.UserEntity;
import ru.otus.homework.users.exception.NotFoundException;
import ru.otus.homework.users.repository.UserRepository;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository petRepository;

    public UserEntity create(UserEntity userEntity) {
        return petRepository.save(userEntity.id(null));
    }

    public UserEntity findById(Long id) {
        return petRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(format("User %s not found.", id)));
    }

    public UserEntity edit(UserEntity userEntity) {
        petRepository.findById(userEntity.id()).orElseThrow(() ->
                new NotFoundException(format("User %s not found", userEntity.id()))
        );
        return petRepository.save(userEntity);
    }

    public void delete(Long id) {
        petRepository.delete(
                petRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(format("User %s not found.", id))
                        )
        );
    }

}
