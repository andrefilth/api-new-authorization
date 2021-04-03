/*
 * @(#)UnauthorizedException.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.handler.exception.unauthorized;

import com.picpay.api.oauth2.infra.handler.exception.ApiException;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 16/10/2020
 */
public abstract class UnauthorizedException extends ApiException {


    protected UnauthorizedException(final String msg, final String code, final String shortMessage) {
        super(msg, code, shortMessage);
    }

    protected UnauthorizedException(final String msg, final String code, final String shortMessage, final Object data) {
        super(msg, code, shortMessage, data);
    }

}
