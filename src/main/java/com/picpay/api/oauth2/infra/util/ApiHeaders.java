
package com.picpay.api.oauth2.infra.util;

import com.picpay.api.oauth2.domain.user.document.Device;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;

import static com.picpay.api.oauth2.domain.user.document.DeviceOs.ANDROID;
import static com.picpay.api.oauth2.domain.user.document.DeviceOs.IOS;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 06/10/2020
 */
@Getter
@Setter
public class ApiHeaders {
    public static final String LIMIT = "X-RateLimit-Limit";
    public static final String REMAINING = "X-RateLimit-Remaining";
    public static final String RESET = "X-RateLimit-Reset";

    public static final String X_TOTAL_COUNT = "X-Total-Count";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String IP = X_FORWARDED_FOR;

    public static final String DEVICE_ID = "device_id";
    public static final String ANDROID_ID = "android_id";
    public static final String DEVICE_OS = "device_os";
    public static final String DEVICE_MODEL = "device_model";

    public static final String APP_VERSION = "app_version";
    public static final String INSTALLATION_ID = "installation_id";

    public static final String TIMEZONE = "timezone";
    public static final String CONSUMER_ID = "consumer_id";

    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

    public static final String SYNCDATA = "syncdata";


    private Map<String, String> headers;

    public ApiHeaders(@RequestHeader final Map<String, String> headers) {
        if (this.headers == null){
            this.headers = Map.of();
        }
        this.headers = headers;
    }

    public Long getTotalCount() {
        return getLong(X_TOTAL_COUNT);
    }

//    public String getIp() {
//        String ips = get(X_FORWARDED_FOR);
//        return XForwardedForUtils.getClientIp(ips);
//    }

    public String getDeviceId() {
        return get(DEVICE_ID);
    }

    public String getAndroidId() {
        return get(ANDROID_ID);
    }

//    public DeviceOs getDeviceOs() {
//        String header = get(DEVICE_OS);
//        return header == null ? null : DeviceOs.from(header);
//    }

    public String getDeviceModel() {
        return get(DEVICE_MODEL);
    }

    public String getAppVersion() {
        return get(APP_VERSION);
    }

//    public Long getAppVersionAsLong() {
//        return VersionUtil.normalizeVersion(get(APP_VERSION));
//    }

//    public Location getLocation() {
//        Double longitude = getDouble(LONGITUDE);
//        Double latitude = getDouble(LATITUDE);
//        return longitude != null && latitude != null ? Location.of(longitude, latitude) : null;
//    }

    public String getConsumerId() {
        return headers.get("consumer_id");
    }

    public ApiHeaders() {
    }

    public String getInstallationId() {
        return get(INSTALLATION_ID);
    }

    public String getTimezone() {
        return defaultIfNull(get(TIMEZONE), "America/Sao_Paulo");
    }

    public ZoneId getZoneId() {
        return ZoneId.of(getTimezone());
    }
    public Device getDevice(){
        if (Objects.isNull(this.headers.get(ANDROID_ID))){
            return Device
                .builder()
                .deviceId(this.headers.get(DEVICE_ID))
                .deviceModel(this.headers.get(DEVICE_MODEL))
                .installationId(this.headers.get(INSTALLATION_ID))
                .deviceOs(IOS)
                .build();
        }
        return Device
            .builder()
            .deviceId(this.headers.get(DEVICE_ID))
            .deviceModel(this.headers.get(DEVICE_MODEL))
            .installationId(this.headers.get(INSTALLATION_ID))
            .androidId(this.headers.get(ANDROID_ID))
            .deviceOs(ANDROID)
            .build();

    }
    public String get(String headerName) {
        return headers.get(headerName.toLowerCase());
    }

    private Long getLong(String headerName) {
        String value = get(headerName);
        return value.isEmpty() ? null : Long.valueOf(value);
    }

    private Double getDouble(String headerName) {
        String value = get(headerName);
        return value.isEmpty() ? null : Double.valueOf(value);
    }
}

