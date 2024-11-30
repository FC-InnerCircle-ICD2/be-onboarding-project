package com.innercicle.security;

import com.innercicle.domain.Account;
import com.innercicle.domain.AccountRole;
import com.innercicle.security.jwt.CredentialInfo;
import com.innercicle.security.jwt.JwtAuthentication;
import com.innercicle.security.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class DefaultWithMockJwtAuthenticationSecurityContextFactory
    implements WithSecurityContextFactory<WithMockJwtAuthentication> {

    @Override
    public SecurityContext createSecurityContext(WithMockJwtAuthentication annotation) {
        Account account = getAccount(annotation.email());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        JwtAuthenticationToken authentication =
            new JwtAuthenticationToken(
                new JwtAuthentication(account.id(), account.accountId().getId(), account.email(), ""),
                new CredentialInfo(annotation.password()),
                authorities(account.roles())
            );
        context.setAuthentication(authentication);
        return context;
    }

    private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> role) {
        return role.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.name())).collect(Collectors.toSet());
    }

    protected abstract Account getAccount(String email);

}