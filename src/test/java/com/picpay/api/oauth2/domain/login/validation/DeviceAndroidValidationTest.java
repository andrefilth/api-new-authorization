package com.picpay.api.oauth2.domain.login.validation;

import com.picpay.api.oauth2.domain.login.objectValue.LoginDataDecision;
import com.picpay.api.oauth2.domain.user.document.Device;
import com.picpay.api.oauth2.domain.user.document.UserDeviceDocument;
import com.picpay.api.oauth2.domain.user.services.UserDeviceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static com.picpay.api.oauth2.domain.user.document.DeviceOs.ANDROID;
import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.when;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 04/01/2021
 */
@ExtendWith(SpringExtension.class)
class DeviceAndroidValidationTest {

    private DeviceAndroidValidation validation;

    @MockBean
    private UserDeviceService deviceService;

    final String andreConsumerId = "andre-id";
    final Device deviceAndroid = Device
            .builder()
            .androidId("android-id")
            .deviceOs(ANDROID)
            .installationId("installation-id")
            .deviceId("device-id")
            .build();

    @BeforeEach
    public void init(){
        this.validation = new DeviceAndroidValidation(deviceService);
        when(deviceService.findDeviceByConsumerId(andreConsumerId))
                .thenReturn(createUserDeviceDocument(andreConsumerId));
    }



    @DisplayName("Dado um device Android, verificar se já existe é uma reinstalação")
    @Test
    public void test_ddd_011_to_016() {

        final LoginDataDecision decision = LoginDataDecision.builder().consumerId(andreConsumerId).device(deviceAndroid).build();
        final Boolean validate = validation.validate(decision);
        Assertions.assertThat(validate).isTrue();
    }


    private UserDeviceDocument createUserDeviceDocument(String andreConsumerId) {
        return UserDeviceDocument.builder().consumerId(andreConsumerId).id("id").createAt(now()).devices(Set.of(deviceAndroid)).build();
    }

}