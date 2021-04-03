/*
 * @(#)NodeChain.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.node;

import com.picpay.api.oauth2.domain.login.NextChain;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 19/10/2020
 */
public interface NodeChain<T, D> {

    D handle(T argument, NextChain<T, D> nextChain);

}
