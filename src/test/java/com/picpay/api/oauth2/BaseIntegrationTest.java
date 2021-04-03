/*
 * @(#)BaseTest.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2;

import com.picpay.api.oauth2.config.TestRedisConfiguration;
import com.picpay.api.oauth2.domain.user.document.Device;
import com.picpay.api.oauth2.domain.user.document.UserDeviceDocument;
import com.picpay.api.oauth2.domain.user.document.UserDocument;
import com.picpay.api.oauth2.domain.user.repository.UserDeviceRepository;
import com.picpay.api.oauth2.domain.user.repository.UserRepository;
import com.picpay.api.oauth2.infra.dto.request.LoginRequest;
import com.picpay.api.oauth2.restapi.utils.TesterUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.Set;

import static com.picpay.api.oauth2.domain.user.document.DeviceOs.ANDROID;
import static com.picpay.api.oauth2.domain.user.document.UserOrigin.LEGACY;
import static io.restassured.RestAssured.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 05/10/2020
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = TestRedisConfiguration.class)
abstract public class BaseIntegrationTest {

    public static final String path = "/api/oauth";
    @Value("http://localhost:${local.server.port}")
    protected String baseUri;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserDeviceRepository deviceRepository;


    @Autowired
    public PasswordEncoder passwordEncoder;

    protected LoginRequest andreLogin = LoginRequest.builder().password("12345").login("andrefilth").build();
    protected LoginRequest joaoLogin = LoginRequest.builder().password("12345").login("joao").build();

    protected LoginRequest andreBadLogin = LoginRequest.builder().login("andrefilth").password("AAA").build();

    protected TesterUser andreAndroid = TesterUser
        .builder()
        .consumerId("8582779")
        .androidId("andre-android-id")
        .deviceId("andre-device-id")
        .installationId("andre-installation-id")
        .deviceModel("S20")
        .login(andreLogin.getLogin())
        .email("andre.picpay@picpay.com")
        .phone("11950721762")
        .password(andreLogin.getPassword())
        .build();
    protected TesterUser andrePassInvalidAndroid = TesterUser
        .builder()
        .consumerId("8582779")
        .androidId("andre-android-id")
        .deviceId("andre-device-id")
        .installationId("andre-installation-id")
        .deviceModel("S20")
        .login(andreBadLogin.getLogin())
        .email("andre.picpay@picpay.com")
        .phone("11950721762")
        .password("123456789")
        .build();
    protected TesterUser joaoAndroid = TesterUser
        .builder()
        .consumerId("17")
        .androidId("joao-android-id")
        .deviceId("joao-device-id")
        .installationId("joao-installation-id")
        .deviceModel("S10")
        .login(joaoLogin.getLogin())
        .password(joaoLogin.getPassword())
        .build();

    protected TesterUser andreIOS = TesterUser
        .builder()
        .consumerId("8582779")
        .deviceId("andre-device-ios-id")
        .installationId("andre-installation-ios-id")
        .deviceModel("iPhone 11,6")
        .login(andreLogin.getLogin())
        .password(andreLogin.getPassword())
        .build();

    @BeforeEach
    public void init(){
        baseURI = baseUri;
        basePath = path;
        enableLoggingOfRequestAndResponseIfValidationFails();

        // deleteAllDataBases
        userRepository.deleteAll();
        deviceRepository.deleteAll();

    }

    public void insertUserWithOutDevice(final TesterUser testerUser, final long days) {
        userRepository.insert(UserDocument
            .builder()
            .consumerId(testerUser.getConsumerId())
            .userName(testerUser.getLogin())
            .email(testerUser.getEmail())
            .phone(testerUser.getPhone())
            .password(passwordEncoder.encode(testerUser.getPassword()))
            .origin(LEGACY)
            .createdAt(LocalDateTime.now().plusDays(-days))
            .isDeactivated(false)
            .isRestricted(false)
            .build());
        final Device build = Device
            .builder()
            .installationId(testerUser.getInstallationId())
            .deviceId(testerUser.getDeviceId())
            .deviceModel(testerUser.getDeviceModel())
            .deviceOs(ANDROID)
            .androidId(testerUser.getAndroidId())
            .build();
        deviceRepository.save(UserDeviceDocument
            .builder()
            .consumerId(testerUser.getConsumerId())
            .devices(Set.of(build))
            .build());
    }
    public void insertUserWithDevice(final TesterUser testerUser, final Set<Device> devices, final long days) {
        userRepository.insert(UserDocument
            .builder()
            .consumerId(testerUser.getConsumerId())
            .userName(testerUser.getLogin())
            .email(testerUser.getEmail())
            .phone(testerUser.getPhone())
            .password(passwordEncoder.encode(testerUser.getPassword()))
            .origin(LEGACY)
            .createdAt(LocalDateTime.now().plusDays(-days))
            .isDeactivated(false)
            .isRestricted(false)
            .build());
        deviceRepository.save(UserDeviceDocument
            .builder()
            .consumerId(testerUser.getConsumerId())
            .devices(devices)
            .build());
    }
}
