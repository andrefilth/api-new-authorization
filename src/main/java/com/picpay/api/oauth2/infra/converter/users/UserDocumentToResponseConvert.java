/*
 * @(#)UserDocumentToResponseConvert.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.converter.users;

import com.picpay.api.oauth2.domain.user.document.UserDocument;
import com.picpay.api.oauth2.domain.user.objectValue.UserResponse;
import com.picpay.api.oauth2.infra.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 12/10/2020
 */
@Component
public class UserDocumentToResponseConvert implements Converter<UserDocument, UserResponse> {

    @Override
    public UserResponse convert(final UserDocument userDocument) {
        return UserResponse
            .builder()
            .consumerId(userDocument.getConsumerId())
            .origin(userDocument.getOrigin())
            .createdAt(userDocument.getCreatedAt())
            .updatePasswordAt(userDocument.getUpdatePasswordAt())
            .isDeactivated(userDocument.isDeactivated())
            .isRestricted(userDocument.isRestricted())
            .build();
    }

}
