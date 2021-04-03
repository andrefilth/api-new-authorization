/*
 * @(#)UserRepository.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.domain.user.repository;

import com.picpay.api.oauth2.domain.user.document.UserDocument;
import com.picpay.api.oauth2.domain.user.document.UserOrigin;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 08/10/2020
 */
@CacheConfig(cacheNames = "UsersDevices")
public interface UserRepository extends MongoRepository<UserDocument, String> {

     Optional<UserDocument> findByConsumerId(final String consumerId);
     Optional<UserDocument> findByUserNameOrEmailOrPhone(final String userName, final String email, final String phone);
     Optional<UserDocument> findByEmail(final String login);
     Optional<UserDocument> findByUserName(final String login);
     Optional<UserDocument> findByPhone(final String login);
     Page<UserDocument> findByOrigin(final UserOrigin origin, final Pageable page);

     @Override
     @CachePut(key = "#consumerId", condition="#consumerId!=null")
     void deleteAll();

}
