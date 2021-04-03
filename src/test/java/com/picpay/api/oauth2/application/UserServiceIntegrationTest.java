/*
 * @(#)UserServiceTest.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.application;

import com.picpay.api.oauth2.BaseIntegrationTest;
import com.picpay.api.oauth2.domain.user.document.Device;
import com.picpay.api.oauth2.domain.user.document.UserDeviceDocument;
import com.picpay.api.oauth2.domain.user.document.UserDocument;
import com.picpay.api.oauth2.domain.user.objectValue.UserRequest;
import com.picpay.api.oauth2.domain.user.services.UserService;
import com.picpay.api.oauth2.domain.user.document.DeviceOs;
import com.picpay.api.oauth2.domain.user.document.UserOrigin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.picpay.api.oauth2.restapi.utils.UserControllerRequestTestUtils.createRequest;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Esta classe irá testar a integração com o banco de dados
 *
 *
 * @author André Franco
 * @version 1.0 11/10/2020
 */
@ExtendWith(SpringExtension.class)
class UserServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    public UserService userService;


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        deviceRepository.deleteAll();
    }

    @DisplayName("Deve inserir os dados nas duas bases corretamente - DEVICE ANDROID")
    @Test
    public void should_sucess_insert_database() {

        final UserRequest request = createRequest(andreAndroid);
        userService.createUser(request, UserOrigin.LEGACY);

        final UserDocument document = userRepository.findByConsumerId(request.getConsumerId()).orElseGet(null);
        final UserDeviceDocument deviceDocument = deviceRepository.findByConsumerId(request.getConsumerId()).orElseGet(null);

        //user
        assertThat(document.getId()).isNotNull();
        assertThat(document.getConsumerId()).isEqualTo(request.getConsumerId());
        assertThat(document.isDeactivated()).isFalse();
        assertThat(document.isRestricted()).isFalse();
        assertThat(document.getCreatedAt().toLocalDate()).isEqualTo(LocalDateTime.now().toLocalDate());

        //login
        assertThat(document.getUserName()).isEqualTo(request.getUserName());
        assertThat(passwordEncoder.matches(document.getPassword(),request.getPassword()));
        assertThat(document.getCreatedAt().toLocalDate()).isEqualTo(LocalDateTime.now().toLocalDate());

        //user_device
        assertThat(deviceDocument.getId()).isNotNull();
        assertThat(deviceDocument.getConsumerId()).isEqualTo(request.getConsumerId());
        //devices
        final List<Device> devices = new ArrayList<>(deviceDocument.getDevices());
        assertThat(devices.size()).isEqualTo(1);
        assertThat(devices.get(0).getAndroidId()).isEqualTo(andreAndroid.getAndroidId());
        assertThat(devices.get(0).getDeviceId()).isEqualTo(andreAndroid.getDeviceId());
        assertThat(devices.get(0).getInstallationId()).isEqualTo(andreAndroid.getInstallationId());
        assertThat(devices.get(0).getDeviceOs()).isEqualTo(DeviceOs.ANDROID);
        assertThat(devices.get(0).getDeviceModel()).isEqualTo(andreAndroid.getDeviceModel());

    }
//    @DisplayName("Não deve inserir os dados nas duas bases, já que o consumer-ID já existe - DEVICE ANDROID")
//    @Test
//    public void should_not_insert_database() {
//
//        final UserRequest request = createRequest(andreAndroid);
//        userService.createUser(request, LEGACY);
//        final Throwable throwable = catchThrowable(() -> userService.createUser(request, LEGACY));
//        assertThat(throwable)
//            .isInstanceOf(AlreadyExistsException.class)
//            .hasMessage("Outro recurso possui o mesmo valor deste campo na collection: users")
//            ;
//    }
    @DisplayName("Testa chamada ao método")
    @Test
    public void should_select_insert_database() {

        final UserRequest request = createRequest(andreAndroid);
        userService.createUser(request, UserOrigin.LEGACY);
        final String login = "andrefilth";
        final Optional<UserDocument> userNameOrEmailOrPhone = userRepository.findByUserNameOrEmailOrPhone(login, login, login);
        final Optional<UserDocument> document = userRepository.findByEmail(login)
            .or(() -> userRepository.findByUserName(login))
            .or(() -> userRepository.findByPhone(login));

        assertThat(document).isPresent();
        assertThat(userNameOrEmailOrPhone).isPresent();
    }


}