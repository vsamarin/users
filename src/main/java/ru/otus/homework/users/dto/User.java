package ru.otus.homework.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.otus.homework.users.validator.UserNameUniqueConstraint;

@Getter
@Setter
@Accessors(chain = true)
@UserNameUniqueConstraint
public class User {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Positive
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String username;

    @NotBlank
    @Size(min = 1, max = 255)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 255)
    private String lastName;

    @NotBlank
    @Size(min = 1, max = 255)
    private String email;

    @NotBlank
    @Size(min = 1, max = 255)
    private String phone;

}
