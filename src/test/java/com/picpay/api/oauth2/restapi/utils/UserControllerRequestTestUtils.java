/*
 * @(#)UserControllerRequestTestUtils.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.restapi.utils;

import com.picpay.api.oauth2.domain.user.document.UserOrigin;
import com.picpay.api.oauth2.domain.user.objectValue.UserRequest;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
public class UserControllerRequestTestUtils {
    public static ValidatableResponse createUser(final TesterUser user, final UserOrigin origin) {
        return given()
            .log().all()
            .contentType(JSON)
            .body(createRequest(user))
            .header("origin", origin)
            .post("users")
            .then()
            ;
    }
    public static ValidatableResponse findAllUsers(final int page, final int size) {

        return given()
            .log().all()
            .contentType(JSON)
            .queryParam("page", page)
            .queryParam("size", size)
            .get("users")
            .then()
            ;
    }

    public static UserRequest createRequest(final TesterUser andreAndroid) {
        return UserRequest
            .builder()
            .consumerId(andreAndroid.getConsumerId())
            .userName(andreAndroid.getLogin())
            .email(andreAndroid.getEmail())
            .phoneNumber(andreAndroid.getPhone())
            .password(andreAndroid.getPassword())
            .androidId(andreAndroid.getAndroidId())
            .deviceId(andreAndroid.getDeviceId())
            .deviceModel(andreAndroid.getDeviceModel())
            .installationId(andreAndroid.getInstallationId())
            .build();
    }

}
