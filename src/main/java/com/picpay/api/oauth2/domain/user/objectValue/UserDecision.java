/*
 * @(#)UserDecision.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.user.objectValue;

import com.picpay.api.oauth2.domain.user.document.UserOrigin;
import lombok.Builder;
import lombok.Getter;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
@Getter
@Builder
public class UserDecision {

    private final UserRequest request;
    private final UserOrigin origin;

}
