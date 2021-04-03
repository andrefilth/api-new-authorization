/*
 * @(#)UserDeviceRepository.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.user.repository;

import com.picpay.api.oauth2.domain.user.document.UserDeviceDocument;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 09/10/2020
 */

@CacheConfig(cacheNames = "UserDeviceService")
public interface UserDeviceRepository extends MongoRepository<UserDeviceDocument, String> {

    Optional<UserDeviceDocument> findByConsumerId(final String consumerId);

    @Override
    @CacheEvict(allEntries = true)
    void deleteAll();
}
