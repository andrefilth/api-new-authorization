/*
 * @(#)UserOriginNotFoundException.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.handler.exception.notfound;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
public class UserOriginNotFoundException extends NotFoundException {


    private static final String INVALID_USER_ORIGIN = "Invalid User Origin";
    private static final String ORIGIN_NOT_FOUND = "origin_not_found";

    public UserOriginNotFoundException() {
        super(INVALID_USER_ORIGIN, ORIGIN_NOT_FOUND, INVALID_USER_ORIGIN);
    }

}
