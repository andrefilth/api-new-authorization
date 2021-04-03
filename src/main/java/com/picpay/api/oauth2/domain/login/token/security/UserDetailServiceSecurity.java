/*
 * @(#)UserDetailServiceSecurity.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.token.security;

import com.picpay.api.oauth2.domain.user.document.UserDocument;
import com.picpay.api.oauth2.domain.user.repository.UserRepository;
import com.picpay.api.oauth2.infra.handler.exception.unauthorized.InvalidUsernameUnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 14/10/2020
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailServiceSecurity implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public User loadUserByUsername(final String userName){
        return repository
            .findByUserNameOrEmailOrPhone(userName, userName, userName)
            .map(user -> createUser(user))
            .orElseThrow(InvalidUsernameUnauthorizedException::new);
    }

    protected User createUser(final UserDocument user) {
        return new User(
            user.getUserName(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
