/*
 * @(#)Converter.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.converter;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 16/12/2019
 */
@FunctionalInterface
public interface Converter<T, D> {

    D convert(final T t);

}
