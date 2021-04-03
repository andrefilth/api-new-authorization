/*
 * @(#)LoginControllerRequestTestUtils.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.restapi.utils;

import com.picpay.api.oauth2.infra.dto.request.LoginRequest;
import com.picpay.api.oauth2.infra.util.ApiHeaders;
import com.picpay.api.oauth2.domain.user.document.DeviceOs;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 05/10/2020
 */
public class LoginControllerRequestTestUtils {
//    public static ValidatableResponse requestLogin(final LoginRequest andreLogin,
//        final TesterUser andreAndroid) {
//        return given()
//            .log().all()
//            .contentType(JSON)
//            .body(andreLogin)
//            .post("login")
//            .then()
//        ;
//    }


    public static ValidatableResponse requestLogin(final LoginRequest andreLogin, final TesterUser testerUser) {
        if (testerUser.getAndroidId() != null) {
           return given()
                .contentType(JSON)
                .header(ApiHeaders.INSTALLATION_ID, testerUser.getInstallationId())
                .header(ApiHeaders.DEVICE_OS, DeviceOs.ANDROID)
                .header(ApiHeaders.DEVICE_MODEL, testerUser.getDeviceModel())
                .header(ApiHeaders.ANDROID_ID, testerUser.getAndroidId())
                .header(ApiHeaders.DEVICE_ID, testerUser.getDeviceId())
                .header(ApiHeaders.CONSUMER_ID, testerUser.getConsumerId())
                .header(ApiHeaders.APP_VERSION, "")
                .body(andreLogin)
                .post("login")
                .then();
        }
        return  given()
            .header(ApiHeaders.INSTALLATION_ID, testerUser.getInstallationId())
            .header(ApiHeaders.DEVICE_OS, DeviceOs.IOS)
            .header(ApiHeaders.DEVICE_MODEL, testerUser.getDeviceModel())
            .header(ApiHeaders.DEVICE_ID, testerUser.getDeviceId())
            .header(ApiHeaders.CONSUMER_ID, testerUser.getConsumerId())
            .header(ApiHeaders.APP_VERSION, "")
            .contentType(JSON)
            .body(andreLogin)
            .post("login")
            .then();
    }

}
