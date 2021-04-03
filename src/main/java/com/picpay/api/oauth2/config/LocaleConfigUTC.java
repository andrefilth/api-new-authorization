/*
 * @(#)LocaleConfigUTC.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 11/10/2020
 */
@Slf4j
@Configuration
public class LocaleConfigUTC {

    @Bean
    public void timeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        log.info("Date in UTC: {}", LocalDateTime.now().toString());

    }
}
