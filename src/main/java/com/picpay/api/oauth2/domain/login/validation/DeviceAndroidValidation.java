/*
 * @(#)DeviceAndroidValidation.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.validation;

import com.picpay.api.oauth2.domain.login.objectValue.LoginDataDecision;
import com.picpay.api.oauth2.domain.user.document.Device;
import com.picpay.api.oauth2.domain.user.services.UserDeviceService;
import com.picpay.api.oauth2.infra.handler.exception.unauthorized.TFAException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Objects.*;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 28/10/2020
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceAndroidValidation implements DeviceValidation<LoginDataDecision, Boolean> {

    private final UserDeviceService deviceService;


    @Override
    public  Boolean validate(final LoginDataDecision decision) {
        final var device = decision.getDevice();
        final var consumerId = decision.getConsumerId();
        final var deviceDocument = deviceService.findDeviceByConsumerId(consumerId);

        final var devices = deviceDocument.getDevices();
        final var sameDeviceId = isSameDeviceId(device, devices);
        final var sameAndroidId = isSameAndroidId(device, devices);
        if (!sameAndroidId){
            log.info("consumer [ {} ] reinstalled app android, start TFA event ", consumerId);
            return true;
        }
        if (!sameDeviceId){
            log.info("consumer [ {} ] installed app android on a new device, start TFA event", consumerId);
            return true;
        }
        return false;
    }

    private boolean isSameAndroidId(Device device, Set<Device> devices) {
        return devices
                .stream()
                .filter(androidId -> nonNull(androidId.getAndroidId()))
                .anyMatch(android -> android.getAndroidId().equalsIgnoreCase(device.getAndroidId()));
    }

    private boolean isSameDeviceId(Device device, Set<Device> devices) {
        return devices
                .stream()
                .anyMatch(deviceId -> deviceId.getDeviceId().equalsIgnoreCase(device.getDeviceId()));
    }

    private boolean isSameInstallId(Device device, Set<Device> devices) {
        return devices
                .stream()
                .anyMatch(install -> install.getInstallationId().equalsIgnoreCase(device.getInstallationId()));
    }

}
