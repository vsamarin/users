package ru.otus.homework.billing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.billing.controller.examples.AccountExamples;
import ru.otus.homework.billing.dto.Account;
import ru.otus.homework.billing.dto.Amount;
import ru.otus.homework.billing.mapper.AccountMapper;
import ru.otus.homework.billing.service.AccountService;


@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Tag(name = "Account", description = "Operations about user account")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @Operation(
            operationId = "account_find",
            summary = "Find user account",
            description = "Find user account"
    )
    @GetMapping(
            path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "user account",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Account.class),
                            examples = @ExampleObject(value = AccountExamples.GET)
                    )
            )
    })
    public Account find(
            @Parameter(description = "ID of user", example = "1")
            @PathVariable("userId") Long userId
    ) {
        return accountMapper.map(accountService.findById(userId));
    }

    @Operation(
            operationId = "deposit",
            summary = "Deposit",
            description = "Put money on user account"
    )
    @PutMapping(path = "/{userId}/deposit",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "user account",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Amount.class),
                            examples = @ExampleObject(value = AccountExamples.DEPOSIT)
                    )
            )
    })
    public Account deposit(
            @Parameter(description = "ID of user", name = "userId")
            @PathVariable("userId") Long userId,
            @RequestBody Amount amount
    ) {
        return accountMapper.map(accountService.deposit(userId, amount.getAmount()));
    }

}
