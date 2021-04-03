/*
 * @(#)AlreadyExistsException.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.handler.exception;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
public class AlreadyExistsException extends ApiException {
    private static final String MESSAGE = "Outro recurso possui o mesmo valor deste campo na collection: ";
    private static final String CODE = "already_exists";
    private static final String SHORT_CODE = "recurso já utilizado";

    public AlreadyExistsException(final String msg) {
        super(MESSAGE + msg, CODE, SHORT_CODE);
    }

}
