/*
 * @(#)LoginRequest.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.picpay.api.oauth2.infra.annotation.Password;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 05/10/2020
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@AllArgsConstructor
public class LoginRequest {

    @ApiModelProperty(
        example = "joao",
        required = true
    )
    private final String login;


    @ApiModelProperty(
        example = "12345",
        required = true
    )

    @Password
    private final String password;

}
