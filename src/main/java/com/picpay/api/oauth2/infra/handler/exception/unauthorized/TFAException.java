/*
 * @(#)TFAException.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.handler.exception.unauthorized;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 29/10/2020
 */
public class TFAException extends UnauthorizedException {



    public TFAException() {
        super("O dispositivo precisa realizar TFA", "tfa_code", "iniciar TFA");
    }

}
