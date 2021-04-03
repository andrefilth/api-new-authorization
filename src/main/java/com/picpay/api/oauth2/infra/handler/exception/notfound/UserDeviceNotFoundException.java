/*
 * @(#)UserDeviceNotFoundException.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.handler.exception.notfound;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 20/10/2020
 */
public class UserDeviceNotFoundException extends NotFoundException {

    private static final String ORIGIN_NOT_FOUND = "user_device_not_found";

    public UserDeviceNotFoundException(final String msg) {
        super(msg, ORIGIN_NOT_FOUND, msg);
    }

    protected UserDeviceNotFoundException(final String msg, final String code, final String shortMessage, final Object data) {
        super(msg, code, shortMessage, data);
    }

}
