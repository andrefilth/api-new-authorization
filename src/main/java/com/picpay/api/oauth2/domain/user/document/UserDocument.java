/*
 * @(#)UserDocument.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.user.document;

import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.index.IndexDirection.ASCENDING;

@Builder
@Getter
@Document(collection = "users_v2")
public class UserDocument {

    @Id
    private final String id;

    @Indexed(name = "{ consumerId: 1 }", unique = true, direction = ASCENDING)
    private final String consumerId;
    @Indexed
    private final String userName;
    @Indexed
    private final String email;
    @Indexed
    private final String phone;
    @With
    private String password;

    private final LocalDateTime createdAt;

    private LocalDateTime updateAt;
    @With
    private LocalDateTime updatePasswordAt;

    @Indexed
    private UserOrigin origin;

    @Version
    private Long version;

    private boolean isDeactivated;
    private boolean isRestricted;


    public void updatePassword(final String newPassword) {
        this.password = newPassword;
        this.updatePasswordAt = LocalDateTime.now();
    }

}