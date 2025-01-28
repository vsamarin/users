package ru.otus.homework.billing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.billing.entity.AccountEntity;
import ru.otus.homework.billing.exception.NotFoundException;
import ru.otus.homework.billing.repository.AccountRepository;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountEntity create(Long userId) {
        AccountEntity account = new AccountEntity();
        account.userId(userId);
        account.balance(0L);
        return accountRepository.save(account);
    }

    public AccountEntity findById(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(format("User %s not found.", id)));
    }

    public AccountEntity deposit(long userId, long amount) {
        AccountEntity account = findById(userId);
        account.balance(account.balance() + amount);
        return accountRepository.save(account);
    }

    public AccountEntity withdraw(long userId, long amount) {
        AccountEntity account = findById(userId);
        account.balance(account.balance() - amount);
        if (account.balance() < 0) {
            throw new IllegalStateException(String.format("User %s balance is negative", userId));
        }
        return accountRepository.save(account);
    }

}
