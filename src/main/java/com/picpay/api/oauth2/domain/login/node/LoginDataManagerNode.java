/*
 * @(#)LoginDataManagerNode.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.login.node;

import com.picpay.api.oauth2.domain.login.NextChain;
import com.picpay.api.oauth2.domain.login.objectValue.LoginDataDecision;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 19/10/2020
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginDataManagerNode {

    private final List<NodeChain<LoginDataDecision, Boolean>> nodes;

    public Boolean next(final LoginDataDecision decision) {
        return new LoginDataStreamNextChain(nodes).next(decision);
    }

    private static class LoginDataStreamNextChain extends NextList<LoginDataDecision, Boolean> {
        public LoginDataStreamNextChain(
            final List<NodeChain<LoginDataDecision, Boolean>> nodes) {
            super(nodes);
        }

        @Override
        protected Boolean notfound(final LoginDataDecision argument) {
            return null;
        }

    }

}
