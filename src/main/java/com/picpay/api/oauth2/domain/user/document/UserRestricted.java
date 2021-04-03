/*
 * @(#)UserRestricted.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.user.document;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
@Builder
@Getter
public class UserRestricted {

    private final String restrictionType;
    private final String consumerOrderRestriction;
    private final String detail;
    private boolean isRestricted;
    private final LocalDateTime createdAt;
    private final LocalDateTime updateAt;

}
