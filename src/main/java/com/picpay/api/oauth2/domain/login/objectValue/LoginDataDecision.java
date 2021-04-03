/*
 * @(#)LoginDataDecision.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.objectValue;

import com.picpay.api.oauth2.domain.user.document.Device;
import com.picpay.api.oauth2.domain.user.document.UserDeviceDocument;
import com.picpay.api.oauth2.infra.dto.request.LoginRequest;
import lombok.Builder;
import lombok.Getter;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 19/10/2020
 */
@Builder
@Getter
public class LoginDataDecision {

    private final LoginRequest login;
    private final Device device;
    private final UserDeviceDocument userDevice;
    private final String consumerId;


}
