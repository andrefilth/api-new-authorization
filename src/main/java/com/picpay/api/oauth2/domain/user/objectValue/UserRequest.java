/*
 * @(#)UserRequest.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.user.objectValue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.picpay.api.oauth2.infra.annotation.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 06/10/2020
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@AllArgsConstructor
public class UserRequest {

    @JsonProperty("consumer_id")
    private final String consumerId;


    @Password
    private final String password;

    @JsonProperty("user_name")
    private final String userName;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("phone_number")
    private final String phoneNumber;

    @JsonProperty("installation_id")
    private final String installationId;

    @JsonProperty("android_id")
    private final String androidId;

    @JsonProperty("device_id")
    private final String deviceId;

    @JsonProperty("device_model")
    private final String deviceModel;

}
