package com.survey.www.commons.config.security.service;

import com.survey.www.accounts.domain.Account;
import com.survey.www.accounts.repository.AccountRepository;
import com.survey.www.commons.config.security.jwt.dto.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Account account = accountRepository.findByPhone(userId).orElseThrow(() -> new InternalAuthenticationServiceException("아이디 또는 비밀번호가 일치하지 않습니다"));
        return new LoginUser(account);
    }
}
