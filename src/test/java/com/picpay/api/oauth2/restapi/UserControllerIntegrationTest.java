/*
 * @(#)UserControllerTest.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.restapi;

import com.picpay.api.oauth2.BaseIntegrationTest;
import com.picpay.api.oauth2.domain.user.document.UserOrigin;
import com.picpay.api.oauth2.restapi.utils.UserControllerRequestTestUtils;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
@ExtendWith(SpringExtension.class)
class UserControllerIntegrationTest extends BaseIntegrationTest {


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        deviceRepository.deleteAll();
    }

    @DisplayName("Deve retornar sucesso na validação do login e da senha. Status 201")
    @Test
    public void should_create_sucess(){

        final ValidatableResponse response = UserControllerRequestTestUtils.createUser(andreAndroid, UserOrigin.LEGACY).log().all();
        response
            .statusCode(201)
        ;
    }
    @DisplayName("Deve retornar com sucesso uma lista paginada de user. Status 302")
    @Test
    public void should_get_list_page_sucess(){

        insertUserWithOutDevice(andreAndroid, 10);
        insertUserWithOutDevice(joaoAndroid, 3);
        final ValidatableResponse response = UserControllerRequestTestUtils.findAllUsers(0, 10);
        response
            .statusCode(302)
            .body("$", hasSize(2))
            .body("consumer_id[0]", is("17"))
            .body("consumer_id[1]", is("8582779"))
            .body("origin", hasItems("LEGACY", "LEGACY"))
            ;
    }

}