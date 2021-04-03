/*
 * @(#)DeviceIOSValidation.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.validation;

import com.picpay.api.oauth2.domain.login.objectValue.LoginDataDecision;
import com.picpay.api.oauth2.domain.user.document.Device;
import com.picpay.api.oauth2.domain.user.services.UserDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 31/10/2020
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceIOSValidation {

    private final UserDeviceService deviceService;

    public Boolean validate(final LoginDataDecision decision) {
        final var device = decision.getDevice();
        final var consumerId = decision.getConsumerId();
        final var deviceDocument = deviceService.findDeviceByConsumerId(consumerId);

        final Set<Device> devices = deviceDocument.getDevices();
        final var sameInstallId = devices.stream().anyMatch(install -> install.getInstallationId().equalsIgnoreCase(device.getInstallationId()));
        final var sameDeviceId = devices.stream().anyMatch(deviceId -> deviceId.getDeviceId().equalsIgnoreCase(device.getDeviceId()));
        if (!sameInstallId){
            log.info("consumer [ {} ] reinstalled app ios, start TFA event ", consumerId);
            return true;
        }
        if (!sameDeviceId){
            log.info("consumer [ {} ] installed app ios on a new device, start TFA event", consumerId);
            return true;
        }
        return false;
    }

}
