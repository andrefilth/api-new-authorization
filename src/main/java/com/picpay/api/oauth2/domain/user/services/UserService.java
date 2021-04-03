/*
 * @(#)UserService.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.user.services;

import com.picpay.api.oauth2.domain.user.document.Device;
import com.picpay.api.oauth2.domain.user.document.UserDeviceDocument;
import com.picpay.api.oauth2.domain.user.document.UserDocument;
import com.picpay.api.oauth2.domain.user.document.UserOrigin;
import com.picpay.api.oauth2.domain.user.objectValue.UserDecision;
import com.picpay.api.oauth2.domain.user.objectValue.UserRequest;
import com.picpay.api.oauth2.domain.user.objectValue.UserResponse;
import com.picpay.api.oauth2.domain.user.repository.UserRepository;
import com.picpay.api.oauth2.infra.converter.users.UserDocumentToResponseConvert;
import com.picpay.api.oauth2.infra.converter.users.UserRequestToDocumentConvert;
import com.picpay.api.oauth2.infra.converter.users.UserRequestToUserDeviceDocumentConvert;
import com.picpay.api.oauth2.infra.dto.request.DeviceRequest;
import com.picpay.api.oauth2.infra.dto.response.DeviceResponse;
import com.picpay.api.oauth2.infra.dto.response.UserDeviceResponse;
import com.picpay.api.oauth2.infra.handler.exception.AlreadyExistsException;
import com.picpay.api.oauth2.infra.handler.exception.UsersNoContentException;
import com.picpay.api.oauth2.infra.handler.exception.unauthorized.InvalidUsernameUnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 06/10/2020
 */
@Slf4j
@RequiredArgsConstructor
@Service
@CacheConfig(cacheNames = "Users")
public class UserService {

    private final UserRepository repository;
    private final UserDeviceService userDeviceService;
    private final UserRequestToDocumentConvert toDocumentConvert;
    private final UserDocumentToResponseConvert toResponseConvert;
    private final UserRequestToUserDeviceDocumentConvert toUserDeviceDocumentConvert;

    public void createUser(final UserRequest userRequest, final UserOrigin origin) {

        final UserDecision userDecision = UserDecision.builder().request(userRequest).origin(origin).build();
        final UserDocument document = toDocumentConvert.convert(userDecision);
        final UserDeviceDocument deviceDocument = toUserDeviceDocumentConvert.convert(userDecision);
        try {
            repository.insert(document);
            userDeviceService.updateOrSave(deviceDocument);
            log.info("event user created consumer: [ {} ] ", document.getConsumerId());
        }catch (DuplicateKeyException e){
            log.error("duplicate key consumer [ {} ], [ {} ] ", document.getConsumerId(), e.getMessage());
            throw new AlreadyExistsException("users");
        }
    }

    @Cacheable(key = "#login")
    public Optional<UserDocument> findUserByLogin(final String login){
        return repository
            .findByUserNameOrEmailOrPhone(login, login, login);
    }
    @Cacheable(key = "#consumerId")
    public Optional<UserDocument> findUserByLogin(final String consumerId, final String login){
        return repository
            .findByUserNameOrEmailOrPhone(login, login, login);
    }
    public List<UserResponse> findUserList(final UserOrigin origin, final Pageable page) {

        if (isNull(origin)){
            return getCollect(page);
        }
        return getCollectByOrigin(origin, page);
    }

    private List<UserResponse> getCollectByOrigin(final UserOrigin origin, final Pageable page) {
        final List<UserResponse> userResponses = repository
            .findByOrigin(origin, page)
            .stream()
            .map(toResponseConvert::convert)
            .collect(Collectors.toList());
        if (userResponses.isEmpty()){
            throw new UsersNoContentException();
        }
        return userResponses;
    }

    private List<UserResponse> getCollect(final Pageable page) {
        final Page<UserDocument> all = repository
            .findAll(page);
        final List<UserResponse> userResponses = all
            .stream()
            .map(toResponseConvert::convert)
            .collect(Collectors.toList());
        if (userResponses.isEmpty()){
            throw new UsersNoContentException();
        }
        return userResponses;
    }

    public void updateDevice(final String consumerId,
        final @Valid DeviceRequest deviceRequest){
        final Device device = Device
            .builder()
            .deviceOs(deviceRequest.getDeviceOs())
            .deviceId(deviceRequest.getDeviceId())
            .installationId(deviceRequest.getInstallationId())
            .deviceModel(deviceRequest.getDeviceModel())
            .androidId(deviceRequest.getAndroidId())
            .build();
        var deviceDocument = userDeviceService.findDeviceByConsumerId(consumerId);
        deviceDocument.addDevices(device);
        userDeviceService.updateOrSave(deviceDocument);
    }


    public UserDeviceResponse findDeviceByConsumerId(final String consumerId) {
        final UserDeviceDocument deviceByConsumerId = userDeviceService.findDeviceByConsumerId(consumerId);
        final Set<DeviceResponse> responses = deviceByConsumerId.getDevices().stream().map(this::createResponse).collect(Collectors.toSet());
        return UserDeviceResponse.builder().consumerId(deviceByConsumerId.getConsumerId()).devices(responses).build();
    }

    private DeviceResponse createResponse(final Device device) {
        return DeviceResponse
            .builder()
            .deviceOs(device.getDeviceOs())
            .deviceId(device.getDeviceId())
            .deviceModel(device.getDeviceModel())
            .installationId(device.getInstallationId())
            .androidId(device.getAndroidId())
            .build();
    }

}
