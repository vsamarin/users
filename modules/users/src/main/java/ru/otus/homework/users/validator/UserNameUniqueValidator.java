package ru.otus.homework.users.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.otus.homework.users.dto.User;
import ru.otus.homework.users.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserNameUniqueValidator implements ConstraintValidator<UserNameUniqueConstraint, User> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(User userDto, ConstraintValidatorContext cxt) {
        if (cxt instanceof HibernateConstraintValidatorContext) {
            cxt.unwrap(HibernateConstraintValidatorContext.class).addMessageParameter("field", "name");
        }
        return (userDto.getId() == null) ?
                userRepository.findOneByUsername(userDto.getUsername()).isEmpty() :
                userRepository.findOneByIdNotAndUsername(userDto.getId(), userDto.getUsername()).isEmpty();
    }

}
