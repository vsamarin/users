package ru.otus.homework.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.users.controller.examples.UserExamples;
import ru.otus.homework.users.dto.Error;
import ru.otus.homework.users.dto.User;
import ru.otus.homework.users.exception.NotFoundException;
import ru.otus.homework.users.mapper.UserMapper;
import ru.otus.homework.users.service.UserService;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Operations about user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            operationId = "user_create",
            summary = "Create user",
            description = "This can only be done by the logged in user."
    )
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created user object",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class),
                            examples = @ExampleObject(value = UserExamples.CREATE)
                    )
            )
    })
    public User create(
            @Valid
            @Schema(example = UserExamples.CREATE)
            @RequestBody User userDto
    ) {
        return userMapper.map(userService.create(userMapper.map(userDto)));
    }

    @Operation(
            operationId = "user_find",
            summary = "Find user",
            description = "Returns a user based on a single ID, if the user does not have access to the user"
    )
    @GetMapping(
            path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "user response",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class),
                            examples = @ExampleObject(value = UserExamples.GET)
                    )
            ),
            @ApiResponse(
                    description = "unexpected error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Error.class),
                            examples = @ExampleObject(value = UserExamples.UNEXPECTED_ERROR)
                    )
            )
    })
    public User find(
            @Parameter(description = "ID of user", example = "1")
            @PathVariable("userId") Long userId
    ) {
        return userMapper.map(userService.findById(userId));
    }

    @Operation(
            operationId = "user_edit",
            summary = "Update user",
            description = "Update user with User ID supplied"
    )
    @PutMapping(path = "/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "user updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Error.class),
                            examples = @ExampleObject(value = UserExamples.UPDATE)
                    )
            ),
            @ApiResponse(
                    description = "unexpected error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Error.class),
                            examples = @ExampleObject(value = UserExamples.UNEXPECTED_ERROR)
                    )
            )
    })
    public User edit(
            @Parameter(description = "ID of user", name = "userId")
            @PathVariable("userId") Long userId,
            @Valid
            @RequestBody User userDto
    ) {
        return userMapper.map(userService.edit(userMapper.map(userId, userDto)));
    }

    @Operation(
            operationId = "user_delete",
            summary = "delete user",
            description = "deletes a single user based on the ID supplied"
    )
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "user deleted"
            ),
            @ApiResponse(
                    description = "unexpected error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Error.class),
                            examples = @ExampleObject(value = UserExamples.UNEXPECTED_ERROR)
                    )
            )
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of user", name = "userId")
            @PathVariable("userId") Long userId
    ) {
        try {
            userService.delete(userId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

}
