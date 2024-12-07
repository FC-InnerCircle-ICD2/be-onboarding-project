package com.survey.www.accounts.service;

import com.survey.www.accounts.domain.Account;
import com.survey.www.accounts.exception.AccountException;
import com.survey.www.accounts.exception.AccountExceptionResult;
import com.survey.www.accounts.repository.AccountRepository;
import com.survey.www.commons.config.security.jwt.dto.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public Account getLoginAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return accountRepository.findById(((LoginUser) authentication.getPrincipal()).getAccount().getId()).orElseThrow(() -> new AccountException(AccountExceptionResult.NOT_FOUND_ACCOUNT));
    }

    private Account savedAccount(String phone) {
        return accountRepository.save(createAccount(phone));
    }

    private Account createAccount(String phone) {
        return Account.builder()
                      .phone(phone)
                      .build();
    }
}
