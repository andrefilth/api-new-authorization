/*
 * @(#)UserRequestToUserDeviceDocumentConvert.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.converter.users;

import com.picpay.api.oauth2.domain.user.document.Device;
import com.picpay.api.oauth2.domain.user.document.UserDeviceDocument;
import com.picpay.api.oauth2.domain.user.objectValue.UserDecision;
import com.picpay.api.oauth2.domain.user.objectValue.UserRequest;
import com.picpay.api.oauth2.infra.converter.Converter;
import com.picpay.api.oauth2.domain.user.document.DeviceOs;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Objects.isNull;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 09/10/2020
 */
@Component
public class UserRequestToUserDeviceDocumentConvert implements Converter<UserDecision, UserDeviceDocument> {

    @Override
    public UserDeviceDocument convert(final UserDecision decision) {
        final UserRequest request = decision.getRequest();
        final Device devices = createDeviceByDeviceOS(request);
        return UserDeviceDocument.builder().consumerId(request.getConsumerId()).devices(Set.of(devices)).build();
    }

    private Device createDeviceByDeviceOS(final UserRequest request) {
        if (isNull(request.getAndroidId())){
            return Device.builder()
                .deviceOs(DeviceOs.IOS)
                .deviceId(request.getDeviceId())
                .installationId(request.getInstallationId())
                .deviceModel(request.getDeviceModel())
                .build();
        }

        return Device.builder()
            .deviceOs(DeviceOs.ANDROID)
            .androidId(request.getAndroidId())
            .deviceId(request.getDeviceId())
            .deviceModel(request.getDeviceModel())
            .installationId(request.getInstallationId())
            .build();
    }

}
