/*
 * @(#)ZonedDateTimeSerializer.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 13/10/2020
 */
public class ZonedDateTimeSerializer extends StdSerializer<LocalDateTime> {
    private static ZoneId DATABASE_TIMEZONE = ZoneId.of("America/Sao_Paulo");
    private static ZoneId UTC = ZoneId.of("UTC");

    protected ZonedDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(final LocalDateTime localDateTime, final JsonGenerator jsonGenerator,
        final SerializerProvider serializerProvider) throws IOException {
        ZonedDateTime ldtZoned = localDateTime.atZone(DATABASE_TIMEZONE);
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(UTC);
        jsonGenerator.writeString(utcZoned.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

    }

}
