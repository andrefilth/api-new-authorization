/*
 * @(#)ZonedDateTimeDeserializer.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 13/10/2020
 */
public class ZonedDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    protected ZonedDateTimeDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(final JsonParser jsonParser,
        final DeserializationContext deserializationContext) throws IOException {
        final ZonedDateTime zdt = ZonedDateTime.parse(jsonParser.readValueAs(String.class));
        return LocalDateTime.ofInstant(zdt.toInstant(), ZoneId.systemDefault());
    }

}
