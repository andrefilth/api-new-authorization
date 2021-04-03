/*
 * @(#)ApiException.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.handler.exception;

import com.picpay.api.oauth2.infra.handler.response.ErrorResponse;
import lombok.Getter;

/**
 * Classe criada para poder customizar o retorno sem impactar outros serviços
 */
public abstract class ApiException extends RuntimeException {

    @Getter
    private final String code;

    @Getter
    private String shortMessage;

    @Getter
    private Object data;

    protected ApiException(String msg, String code, String shortMessage) {
        super(msg);
        this.code = code;
        this.shortMessage = shortMessage;
    }


    protected ApiException(String msg, String code, String shortMessage, Object data) {
        super(msg);
        this.code = code;
        this.shortMessage = shortMessage;
        this.data = data;
    }

    public ErrorResponse getErrorModel() {
        return ErrorResponse.builder().code(this.code).message(this.getMessage()).shortMessage(this.getShortMessage()).data(this.data).build();
    }

}

