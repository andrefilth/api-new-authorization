/*
 * @(#)DeviceAndroidNextChain.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.node.nextChain;

import com.picpay.api.oauth2.domain.login.NextChain;
import com.picpay.api.oauth2.domain.login.node.NodeChain;
import com.picpay.api.oauth2.domain.login.objectValue.LoginDataDecision;
import com.picpay.api.oauth2.domain.login.validation.DeviceAndroidValidation;
import com.picpay.api.oauth2.domain.user.document.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.picpay.api.oauth2.domain.user.document.DeviceOs.ANDROID;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 19/10/2020
 */
@RequiredArgsConstructor
@Component
public class DeviceAndroidNextChain implements NodeChain<LoginDataDecision, Boolean> {

    private final DeviceAndroidValidation androidValidation;

    @Override
    public Boolean handle(final LoginDataDecision decision, final NextChain<LoginDataDecision, Boolean> nextChain) {
        final Device device = decision.getDevice();
        if (ANDROID.equals(device.getDeviceOs())){
            return androidValidation.validate(decision);
        }
        return nextChain.next(decision);
    }

}
