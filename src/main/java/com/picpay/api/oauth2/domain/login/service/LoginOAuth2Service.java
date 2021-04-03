/*
 * @(#)LoginOAuth2Service.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.service;

import com.picpay.api.oauth2.domain.login.node.LoginDataManagerNode;
import com.picpay.api.oauth2.domain.login.objectValue.LoginDataDecision;
import com.picpay.api.oauth2.domain.login.token.ManagerSecurityToken;
import com.picpay.api.oauth2.infra.converter.authorization.LoginRequestToAuthTokenConverter;
import com.picpay.api.oauth2.infra.dto.response.LoginResponse;
import com.picpay.api.oauth2.infra.handler.exception.unauthorized.InvalidPasswordUnauthorizedException;
import com.picpay.api.oauth2.infra.handler.exception.unauthorized.TFAException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 31/10/2020
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LoginOAuth2Service {

    private final LoginDataManagerNode managerNode;
    private final LoginRequestToAuthTokenConverter tokenConverter;
    private final AuthenticationManager manager;
    private final ManagerSecurityToken managerSecurityToken;

    public LoginResponse generateToken(final LoginDataDecision decision) {

        final String consumerId = decision.getConsumerId();
        final var token = tokenConverter.convert(decision.getLogin());
        try {
            final Authentication authenticate = manager.authenticate(token);
            final Boolean isNewDevice = managerNode.next(decision);
            if (isNewDevice) {
                log.info("consumer [ {} ] need TFA", consumerId);
                throw new TFAException();
            }
            final User user = (User) authenticate.getPrincipal();
            final String newToken = managerSecurityToken.createToken(user.getUsername());
            log.info("token create consumer: [ {} ]", consumerId);
            return LoginResponse.builder().token(newToken).build();
        } catch (BadCredentialsException e) {
            log.info("token not create consumer: [ {} ]", consumerId);
            throw new InvalidPasswordUnauthorizedException("login/senha inválidos");
        }
    }

}
