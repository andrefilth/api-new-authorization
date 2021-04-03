package com.picpay.api.oauth2.domain.login.node;


import com.picpay.api.oauth2.BaseIntegrationTest;
import com.picpay.api.oauth2.domain.login.objectValue.LoginDataDecision;
import com.picpay.api.oauth2.domain.user.document.Device;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.picpay.api.oauth2.domain.user.document.DeviceOs.IOS;

@ExtendWith(SpringExtension.class)
public class LoginDataManagerNodeTest extends BaseIntegrationTest {

    @Autowired
    protected LoginDataManagerNode node;

    @DisplayName("Dado uma consulta ao banco de dados do consumer, cachear a informação no redis atualizar as informações e recuperar os dados atualizados no cache")
    @Test
    public void teste1(){
        final Device device = Device.builder().deviceOs(IOS).deviceId("device-id").deviceModel("device-model").installationId("installation-id").build();
        final LoginDataDecision build = LoginDataDecision.builder().device(device).build();
        final Boolean next = node.next(build);
        Assertions.assertThat(next).isTrue();

    }
}