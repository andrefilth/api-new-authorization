/*
 * @(#)UserResponse.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.user.objectValue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.picpay.api.oauth2.domain.user.document.UserOrigin;
import com.picpay.api.oauth2.infra.deserializer.ZonedDateTimeDeserializer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 12/10/2020
 */
@JsonPropertyOrder({"consumer_id", "user_name", "email", "phone", "origin", "created_at", "update_password_at"})
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class UserResponse {

    @JsonProperty("consumer_id")
    private final String consumerId;

    @JsonProperty("user_name")
    private final String userName;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("phone")
    private final String phone;

    @JsonProperty("origin")
    private final UserOrigin origin;

    @JsonProperty("created_at")
    @JsonDeserialize(
        using = ZonedDateTimeDeserializer.class
    )
    private LocalDateTime createdAt;

    @JsonProperty("update_password_at")
    @JsonDeserialize(
        using = ZonedDateTimeDeserializer.class
    )
    private LocalDateTime updatePasswordAt;

    @JsonIgnore(value = false)
    private boolean isDeactivated;

    @JsonIgnore(value = false)
    private boolean isRestricted;

}
