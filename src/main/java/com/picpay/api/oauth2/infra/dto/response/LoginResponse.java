/*
 * @(#)LoginResponse.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 12/10/2020
 */
@JsonInclude(value = NON_NULL)
@Builder
@Getter
public class LoginResponse {

//    @JsonProperty("user")
//    private UserResponse user;

    @JsonProperty("token")
    private final String token;


}
