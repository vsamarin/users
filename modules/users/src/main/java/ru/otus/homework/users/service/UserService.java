package ru.otus.homework.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.otus.homework.users.config.RabbitConfig;
import ru.otus.homework.users.entity.UserEntity;
import ru.otus.homework.users.event.AccountCreated;
import ru.otus.homework.users.event.UserCreatedEvent;
import ru.otus.homework.users.exception.NotFoundException;
import ru.otus.homework.users.repository.UserRepository;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;

    public UserEntity create(UserEntity userEntity) {
        UserEntity user = userRepository.save(userEntity.id(null));

        AccountCreated reply = rabbitTemplate.convertSendAndReceiveAsType(
                RabbitConfig.ACCOUNT_EXCHANGE,
                RabbitConfig.ACCOUNT_QUEUE,
                new UserCreatedEvent(user.id()),
                new CorrelationData(String.valueOf(user.id())),
                new ParameterizedTypeReference<>() {
                }
        );

        if (reply != null) {
            return user;
        }
        throw new IllegalStateException(format("Unable to create user %s account", user.id()));
    }

    public UserEntity findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(format("User %s not found.", id)));
    }

    public UserEntity edit(UserEntity userEntity) {
        userRepository.findById(userEntity.id()).orElseThrow(() ->
                new NotFoundException(format("User %s not found", userEntity.id()))
        );
        return userRepository.save(userEntity);
    }

    public void delete(Long id) {
        userRepository.delete(
                userRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(format("User %s not found.", id))
                        )
        );
    }

}
