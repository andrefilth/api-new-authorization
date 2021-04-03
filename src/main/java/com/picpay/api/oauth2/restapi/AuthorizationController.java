/*
 * @(#)AuthorizationController.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.restapi;

import com.picpay.api.oauth2.application.AuthorizationWrapper;
import com.picpay.api.oauth2.infra.dto.request.LoginRequest;
import com.picpay.api.oauth2.infra.dto.response.LoginResponse;
import com.picpay.api.oauth2.infra.dto.response.TokenResponse;
import com.picpay.api.oauth2.infra.handler.exception.unauthorized.InvalidPasswordUnauthorizedException;
import com.picpay.api.oauth2.infra.util.ApiHeaders;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;


/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 05/10/2020
 */
@Api(tags = "Realiza o Login")
@RestController
@RequestMapping(value = "login", produces = "application/json; charset=UTF-8")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationWrapper wrapper;

    @ApiOperation(value = "Criar um token se os dados de login e senha forem válidos")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = TokenResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = InvalidPasswordUnauthorizedException.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = InvalidPasswordUnauthorizedException.class)
    })
    @PostMapping
    @ResponseStatus(CREATED)
    public LoginResponse createToken(@RequestHeader final Map<String, String> headers, @RequestBody @Valid final LoginRequest loginRequest) {


        return wrapper.authorize(loginRequest, new ApiHeaders(headers));
    }

}
