/*
 * @(#)DeviceOs.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.user.document;

import com.picpay.api.oauth2.infra.handler.exception.notfound.DeviceOsNotFoundException;

import java.util.stream.Stream;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
public enum DeviceOs {
    IOS,
    ANDROID;

    public static DeviceOs  getDeviceOs(String value) {
        return Stream.of(values())
            .filter(flow -> flow.name().equals(value))
            .findFirst()
            .orElseThrow(() -> new DeviceOsNotFoundException("Invalid DeviceOs"));
    }
}
