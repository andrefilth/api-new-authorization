/*
 * @(#)DeviceResponse.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.dto.response;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.picpay.api.oauth2.domain.user.document.DeviceOs;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 20/10/2020
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class DeviceResponse implements Serializable {

    @JsonProperty("device_os")
//    @NotNullOrNotEmpty(message = "Operationa System is mandatory")
    @JsonEnumDefaultValue
    private DeviceOs deviceOs;

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("installation_id")
    private String installationId;

    @JsonProperty("device_model")
    private String deviceModel;

    @JsonProperty("android_id")
    private String androidId;

}
