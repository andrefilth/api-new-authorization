/*
 * @(#)Location.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.infra.util;

import lombok.Getter;

import static java.lang.Double.valueOf;
import static java.util.Objects.nonNull;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 19/10/2020
 */
@Getter
public class Location {

    private final Double longitude;
    private final Double latitude;

    public Location(final Double longitude, final Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public Location(final String longitude, final String latitude) {
        this.longitude = nonNull(longitude) ? this.getLongitude(): valueOf(longitude);
        this.latitude = nonNull(latitude) ? this.getLatitude() : valueOf(latitude);
    }

}
