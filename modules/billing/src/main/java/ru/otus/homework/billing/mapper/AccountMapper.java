package ru.otus.homework.billing.mapper;

import org.springframework.stereotype.Component;
import ru.otus.homework.billing.dto.Account;
import ru.otus.homework.billing.entity.AccountEntity;

@Component
public class AccountMapper implements Mapper<AccountEntity, Account> {

    @Override
    public Account map(AccountEntity entity) {
        if (entity == null) return null;
        Account account = new Account();
        account.setUserId(entity.userId());
        account.setBalance(entity.balance());
        return account;
    }

    public AccountEntity map(Account dto) {
        if (dto == null) return null;
        AccountEntity entity = new AccountEntity();
        entity.userId(dto.getUserId());
        entity.balance(dto.getBalance());
        return entity;
    }

}
