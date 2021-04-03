/*
 * @(#)DeviceOsNotFoundException.java 1.0 31/10/2020
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
public class DeviceOsNotFoundException extends NotFoundException {


    public DeviceOsNotFoundException(final String msg) {
        super(msg, "DEVICE_OS", msg);
    }

    protected DeviceOsNotFoundException(final String msg, final String code, final String shortMessage, final Object data) {
        super(msg, code, shortMessage, data);
    }

}
