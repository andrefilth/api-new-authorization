/*
 * @(#)UserDeviceServiceTest.java 1.0 05/11/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.user.services;

import com.picpay.api.oauth2.BaseIntegrationTest;
import com.picpay.api.oauth2.domain.user.document.Device;
import com.picpay.api.oauth2.domain.user.document.UserDeviceDocument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static com.picpay.api.oauth2.domain.user.document.DeviceOs.ANDROID;
import static com.picpay.api.oauth2.domain.user.document.DeviceOs.IOS;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 05/11/2020
 */
@ExtendWith(SpringExtension.class)
class UserDeviceServiceCachingIntegrationIntegrationTest extends BaseIntegrationTest {


    public static final String USER_DEVICE_SERVICE = "UserDeviceService";
    @Autowired
    UserDeviceService deviceService;


    @Autowired
    CacheManager cacheManager;

    @AfterTestMethod
    void tearDown() {
        userRepository.deleteAll();
        deviceRepository.deleteAll();
    }


    private final UserDeviceDocument andreDevices = UserDeviceDocument
        .builder()
        .consumerId(andreAndroid.getConsumerId())
        .devices(Set.of(Device
            .builder()
            .deviceId(andreAndroid.getDeviceId())
            .installationId(andreAndroid.getInstallationId())
            .androidId(andreAndroid.getAndroidId())
            .deviceModel(andreAndroid.getDeviceModel())
            .deviceOs(ANDROID)
            .build()))
        .createAt(LocalDateTime.now())
        .build();
    private final UserDeviceDocument joaoDevices = UserDeviceDocument
        .builder()
        .consumerId("17")
        .devices(Set.of(Device
            .builder()
            .deviceId("joao-device")
            .installationId("joao-installation")
            .deviceModel("Iphone")
            .deviceOs(IOS)
            .build()))
        .createAt(LocalDateTime.now())
        .build();


    @BeforeEach
    public void setUp(){
        deviceRepository.save(andreDevices);
        deviceRepository.save(joaoDevices);
    }

    @DisplayName("Dado uma consulta ao banco de dados do conumer, cachear a informação no redis")
    @Test
    public void testcaching(){

        final UserDeviceDocument deviceDocument = deviceService.findDeviceByConsumerId(andreAndroid.getConsumerId());
        final Optional<UserDeviceDocument> usersDevices = getCachedUsersDevices(deviceDocument.getConsumerId());
        assertThat(of(deviceDocument)).isEqualTo(usersDevices);


    }
    @DisplayName("Dado uma consulta ao banco de dados do conumer, cachear a informação no redis e na segunda consulta não ir no banco de dados")
    @Test
    public void testcaching2(){


        final UserDeviceDocument deviceDocument = deviceService.findDeviceByConsumerId(andreAndroid.getConsumerId());

        deviceService.findDeviceByConsumerId(andreAndroid.getConsumerId());
        final Optional<UserDeviceDocument> usersDevices = getCachedUsersDevices(deviceDocument.getConsumerId());
        assertThat(of(deviceDocument)).isEqualTo(usersDevices);
    }
    @DisplayName("Dado uma consulta ao banco de dados do consumer, cachear a informação no redis atualizar as informações e recuperar os dados atualizados no cache")
    @Test
    public void testcaching3(){

        final var newDevice = Device
                .builder()
                .deviceId("joao-device-2")
                .installationId("joao-installation-2")
                .deviceModel("Iphone-12")
                .deviceOs(IOS)
                .build();

        final var deviceDocument = deviceService.findDeviceByConsumerId(andreAndroid.getConsumerId());
        getCachedUsersDevices(deviceDocument.getConsumerId())
                .ifPresent(document -> {
                    assertThat(document.getUpdateAt()).isNull();
                    assertThat(document.getVersion()).isEqualTo(0);
        });

        deviceDocument.addDevices(newDevice);
        final var update = deviceService.update(deviceDocument);

        final UserDeviceDocument deviceDocument2 = deviceService.findDeviceByConsumerId(update.getConsumerId());
        Optional<UserDeviceDocument> cachedUsersDevices = getCachedUsersDevices(deviceDocument2.getConsumerId());
        cachedUsersDevices
                .ifPresent(document -> {
                    assertThat(document.getUpdateAt().toLocalDate()).isEqualTo(LocalDate.now());
                    assertThat(document.getVersion()).isEqualTo(1);
                });
    }

    private Optional<UserDeviceDocument> getCachedUsersDevices(String consumerId) {
        return ofNullable(cacheManager.getCache(USER_DEVICE_SERVICE))
                .map(c -> c.get(consumerId, UserDeviceDocument.class));
    }

}