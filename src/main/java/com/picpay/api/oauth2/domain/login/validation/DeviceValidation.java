/*
 * @(#)DeviceValidation.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.validation;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 29/10/2020
 */
@FunctionalInterface
public interface DeviceValidation<T, D> {

    D validate(final T decision);

}
