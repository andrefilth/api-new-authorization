/*
 * @(#)ManagerSecurityToken.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.token;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.security.Keys.*;
import static java.lang.System.currentTimeMillis;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 14/10/2020
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ManagerSecurityToken {

    @Value("${picpay.service.auth.jwt.secret}")
    private String secret;

    public String createToken(final String userName) {
        return Jwts.builder()
            .setHeaderParam("alg", "HS256")
            .setHeaderParam("typ", "JWT")
            .setClaims(claim(userName))
            .signWith(hmacShaKeyFor(secret.getBytes()), HS256)
            .compact();
    }

    private Map<String, Object> claim(final String userName) {
        var currentTime = currentTimeMillis() / 1000;
        return Map.of("login", userName, "scope", "*", "exp" , currentTime + 3600, "iat" , currentTime);
    }

}
