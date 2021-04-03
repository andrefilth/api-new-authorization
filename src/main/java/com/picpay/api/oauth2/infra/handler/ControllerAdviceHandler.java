/*
 * @(#)ControllerAdviceHandler.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.handler;

import com.picpay.api.oauth2.infra.extractor.NotValidExceptionToErroRespondeExtractor;
import com.picpay.api.oauth2.infra.handler.exception.AlreadyExistsException;
import com.picpay.api.oauth2.infra.handler.exception.UsersNoContentException;
import com.picpay.api.oauth2.infra.handler.exception.notfound.NotFoundException;
import com.picpay.api.oauth2.infra.handler.exception.unauthorized.UnauthorizedException;
import com.picpay.api.oauth2.infra.handler.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 06/10/2020
 */
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerAdviceHandler {

    private final NotValidExceptionToErroRespondeExtractor fiedToErroRespondeExtractor;


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse handler(final UnauthorizedException exception) {
        return exception.getErrorModel();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handler(final MethodArgumentNotValidException exception) {

        return fiedToErroRespondeExtractor.extract(exception);
    }
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handler(final NotFoundException exception) {
        return exception.getErrorModel();
    }


    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(AlreadyExistsException.class)
    public ErrorResponse handler(final AlreadyExistsException exception) {
        return exception.getErrorModel();
    }

    @ResponseStatus(NO_CONTENT)
    @ExceptionHandler(UsersNoContentException.class)
    public void handler() {
    }

}
