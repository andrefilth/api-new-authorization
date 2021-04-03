/*
 * @(#)FiedToErroRespondeExtractor.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.extractor;

import com.picpay.api.oauth2.infra.handler.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 06/10/2020
 */
@RequiredArgsConstructor
@Component
public class FiedToErroRespondeExtractor implements Extractor<FieldError, ErrorResponse> {

    private final MessageSource messageSource;

    @Override
    public ErrorResponse extract(final FieldError extract) {
        final String message = messageSource.getMessage(extract, LocaleContextHolder.getLocale());
        final String field = extract.getField();
        final String objectName = extract.getObjectName();
        return ErrorResponse
            .builder()
            .message(message)
            .field(field)
            .shortMessage(String.format("class error: %s", objectName))
            .build();
    }

}
