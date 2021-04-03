/*
 * @(#)AuthorizationWrapper.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.application;

import com.picpay.api.oauth2.domain.login.objectValue.LoginDataDecision;
import com.picpay.api.oauth2.domain.login.service.LoginOAuth2Service;
import com.picpay.api.oauth2.infra.dto.request.LoginRequest;
import com.picpay.api.oauth2.infra.dto.response.LoginResponse;
import com.picpay.api.oauth2.infra.util.ApiHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 14/10/2020
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorizationWrapper {

    private final LoginOAuth2Service service;

    public LoginResponse authorize(final LoginRequest loginRequest, final ApiHeaders apiHeaders) {

        final LoginDataDecision decision = LoginDataDecision
            .builder()
            .login(loginRequest)
            .consumerId(apiHeaders.getConsumerId())
            .device(apiHeaders.getDevice())
            .build();


        return service.generateToken(decision);
    }
}



