/*
 * @(#)TokenResponse.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class TokenResponse {

    private String token;

}
