/*
 * @(#)Device.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.user.document;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
@Getter
@Builder
public class Device implements Serializable {

    private DeviceOs deviceOs;
    private String deviceId;
    private String installationId;
    private String deviceModel;
    private String androidId;


}
