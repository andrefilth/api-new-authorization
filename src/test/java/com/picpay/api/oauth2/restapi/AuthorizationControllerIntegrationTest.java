/*
 * @(#)AuthorizationControllerTest.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.restapi;

import com.picpay.api.oauth2.BaseIntegrationTest;
import com.picpay.api.oauth2.domain.user.document.Device;
import com.picpay.api.oauth2.infra.dto.request.LoginRequest;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static com.picpay.api.oauth2.domain.user.document.DeviceOs.ANDROID;
import static com.picpay.api.oauth2.domain.user.document.DeviceOs.IOS;
import static com.picpay.api.oauth2.restapi.utils.LoginControllerRequestTestUtils.requestLogin;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 05/10/2020
 */
@ExtendWith(SpringExtension.class)
class AuthorizationControllerIntegrationTest extends BaseIntegrationTest {

    private final Set<Device> deviceIOS = Set.of(Device
        .builder()
        .installationId(andreIOS.getInstallationId())
        .deviceId(andreIOS.getDeviceId())
        .deviceModel(andreIOS.getDeviceModel())
        .deviceOs(IOS)
        .build());
    private final Set<Device> deviceAndroid = Set.of(Device
        .builder()
        .installationId(joaoAndroid.getInstallationId())
        .deviceId(joaoAndroid.getDeviceId())
        .deviceModel(joaoAndroid.getDeviceModel())
        .androidId(joaoAndroid.getAndroidId())
        .deviceOs(ANDROID)
        .build());

    @AfterEach
    void tearDown() {
        deviceRepository.deleteAll();
    }

    @DisplayName("Deve retornar sucesso na validação do login e da senha. Status 201")
    @Test
    public void test_should_login_sucess(){


        insertUserWithDevice(andreIOS, deviceIOS ,10);
        final ValidatableResponse response = requestLogin(andreLogin, andreIOS).log().all();
        response
            .statusCode(201)
            .body("token", notNullValue())
            ;
    }

    @DisplayName("Deve retornar sucesso na validação do login e da senha porém necessário realizar o TFA. Status 401")
    @Test
    public void test_should_login_sucess_but_need_tfa(){

        insertUserWithDevice(andreAndroid, deviceIOS,10);

        final ValidatableResponse response = requestLogin(andreLogin, andreAndroid);
        response
            .statusCode(401)
            .body("message", is("O dispositivo precisa realizar TFA"))
            .body("code", is("tfa_code"))
            .body("short_message", is("iniciar TFA"))
        ;
    }

    @DisplayName("Deve retornar Unauthorized na validação do login e da senha com senha errada. Status 401")
    @Test
    public void test_should_password_error(){

        insertUserWithOutDevice(andrePassInvalidAndroid, 3);
        final ValidatableResponse response = requestLogin(andreLogin, andreAndroid).log().all();
        response
            .statusCode(401)
            .body("message", is("login/senha inválidos"))
            ;
    }
    @DisplayName("Deve retornar Unauthorized na validação do login e da senha com login errada. Status 401")
    @Test
    public void test_should_login_error(){

        insertUserWithOutDevice(andrePassInvalidAndroid, 3);
        final ValidatableResponse response = requestLogin(LoginRequest.builder().password("12345").login("andrefilth2").build(),
            andreAndroid).log().all();
        response
            .statusCode(401)
            .body("message", is("login/senha inválidos"))
            .body("code", is("invalid_username"))
            .body("short_message", is("login/senha inválidos"))
            ;
    }
    @DisplayName("Deve retornar erro porque a senha contém letrar e tamanho menor que 4 Status 201")
    @Test
    public void test_should_login_fail(){

        final ValidatableResponse response = requestLogin(andreBadLogin, andreAndroid).log().all();
        response
            .statusCode(400)
            .body("message", is("Dados enviados inválidos, verificar campos"))
            .body("code", is("request_fields_error"))
            .body("short_message", is("Requisição com campos inválidos"))

            .body("errors.message", hasSize(2))
            .body("errors.message", hasItem("Senha deve conter apenas números"))
            .body("errors.field", hasItem("password"))
            .body("errors.short_message", hasItem("class error: loginRequest"))

            .body("errors.message", hasItem("tamanho deve ser entre 4 e 10"))
            .body("errors.field", hasItem("password"))
            .body("errors.short_message", hasItem("class error: loginRequest"))
            ;
    }



}