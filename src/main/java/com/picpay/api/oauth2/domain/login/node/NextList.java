/*
 * @(#)NextList.java 1.0 29/06/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.node;

import com.picpay.api.oauth2.domain.login.NextChain;

import java.util.List;

public abstract class NextList<I, O> implements NextChain<I, O> {

    private int position = -1;
    private List<NodeChain<I, O>> nodes;

    public NextList(List<NodeChain<I, O>> nodes) {
        this.nodes = nodes;
    }

    protected abstract O notfound(I argument);

    @Override
    public O next(I argument) {
        if (++position >= nodes.size()) {
            return notfound(argument);
        }
        return nodes.get(position).handle(argument, this);
    }
}
