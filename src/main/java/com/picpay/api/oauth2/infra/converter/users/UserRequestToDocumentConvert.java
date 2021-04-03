/*
 * @(#)UserRequestToDocumentConvert.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.converter.users;

import com.picpay.api.oauth2.domain.user.document.UserDocument;
import com.picpay.api.oauth2.domain.user.objectValue.UserDecision;
import com.picpay.api.oauth2.domain.user.objectValue.UserRequest;
import com.picpay.api.oauth2.infra.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.picpay.api.oauth2.infra.util.SanitizationUtils.sanitizeEmail;
import static com.picpay.api.oauth2.infra.util.SanitizationUtils.sanitizePhone;
import static com.picpay.api.oauth2.infra.util.SanitizationUtils.sanitizeUsername;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
@Component
@RequiredArgsConstructor
public class UserRequestToDocumentConvert implements Converter<UserDecision, UserDocument> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDocument convert(final UserDecision decision) {
        final UserRequest user = decision.getRequest();
        return UserDocument
            .builder()
            .consumerId(user.getConsumerId())
            .userName(sanitizeUsername(user.getUserName()))
            .email(sanitizeEmail(user.getEmail()))
            .phone(sanitizePhone(user.getPhoneNumber()))
            .password(passwordEncoder.encode(user.getPassword()))
            .createdAt(LocalDateTime.now())
            .origin(decision.getOrigin())
            .build();
    }

}
