/*
 * @(#)UserDeviceResponse.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 21/10/2020
 */
@JsonPropertyOrder({"consumer_id", "devices"})
@JsonInclude(value = NON_NULL)
@Builder
@Getter
public class UserDeviceResponse implements Serializable {

    @JsonProperty("consumer_id")
    private String consumerId;

    @JsonProperty("devices")
    private Set<DeviceResponse> devices;
}
