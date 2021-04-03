/*
 * @(#)UserDeviceDocument.java 1.0 31/10/2020
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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;
import static org.springframework.data.mongodb.core.index.IndexDirection.ASCENDING;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
@Builder
@Getter
@Document(collection = "users_device")
public class UserDeviceDocument implements Serializable {

    @Id
    private final String id;

    @Indexed(name = "consumerId", unique = true,  direction = ASCENDING)
    private final String consumerId;

    private Set<Device> devices;

    private LocalDateTime createAt;

    @With
    private LocalDateTime updateAt;

    @Version
    private Long version;


    public UserDeviceDocument addDevices(Device device) {
        if (isNull(devices)) {
            this.devices = Set.of();
        }
        this.devices.add(device);
        this.updateAt = LocalDateTime.now();
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserDeviceDocument that = (UserDeviceDocument) o;
        return consumerId.equals(that.consumerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consumerId);
    }

}
