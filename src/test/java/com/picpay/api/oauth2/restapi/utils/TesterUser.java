/*
 * @(#)TesterUser.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.restapi.utils;

import lombok.Builder;
import lombok.Getter;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
@Builder
@Getter
public class TesterUser {

    private String consumerId;
    private String login;
    private String email;
    private String phone;
    private final String password;
    private final String installationId;
    private final String androidId;
    private final String deviceId;
    private final String deviceModel;

}
