/*
 * @(#)LoginRequestToAuthTokenConverter.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.converter.authorization;

import com.picpay.api.oauth2.infra.converter.Converter;
import com.picpay.api.oauth2.infra.dto.request.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 14/10/2020
 */
@Component
public class LoginRequestToAuthTokenConverter implements Converter<LoginRequest, UsernamePasswordAuthenticationToken> {


    @Override
    public UsernamePasswordAuthenticationToken convert(final LoginRequest loginRequest) {
        return new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword());
    }

}
