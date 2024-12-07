package com.survey.www.accounts.repository;

import com.survey.www.accounts.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByPhone(String username);
}
