/*
 * @(#)UsersNoContentException.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.handler.exception;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 12/10/2020
 */
public class UsersNoContentException extends ApiException {

    public UsersNoContentException() {
        super("Lista vazia", "empty_list", "Lista vazia");
    }

}
