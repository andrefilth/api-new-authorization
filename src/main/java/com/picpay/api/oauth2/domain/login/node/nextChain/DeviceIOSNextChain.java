/*
 * @(#)DeviceIOSNextChain.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.node.nextChain;

import com.picpay.api.oauth2.domain.login.NextChain;
import com.picpay.api.oauth2.domain.login.node.NodeChain;
import com.picpay.api.oauth2.domain.login.objectValue.LoginDataDecision;
import com.picpay.api.oauth2.domain.login.validation.DeviceIOSValidation;
import com.picpay.api.oauth2.domain.user.document.Device;
import com.picpay.api.oauth2.domain.user.document.DeviceOs;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 31/10/2020
 */
@RequiredArgsConstructor
@Component
public class DeviceIOSNextChain implements NodeChain<LoginDataDecision, Boolean> {

    private final DeviceIOSValidation validation;

    @Override
    public Boolean handle(final LoginDataDecision decision, final NextChain<LoginDataDecision, Boolean> nextChain) {
        final Device device = decision.getDevice();
        if (DeviceOs.IOS.equals(device.getDeviceOs())){
            return validation.validate(decision);
        }
        return nextChain.next(decision);
    }

}
