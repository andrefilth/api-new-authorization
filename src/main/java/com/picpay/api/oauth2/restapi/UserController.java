/*
 * @(#)UserController.java 1.0 31/10/2020
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.picpay.api.oauth2.restapi;

import com.picpay.api.oauth2.domain.user.services.UserService;
import com.picpay.api.oauth2.domain.user.document.UserOrigin;
import com.picpay.api.oauth2.domain.user.objectValue.UserRequest;
import com.picpay.api.oauth2.domain.user.objectValue.UserResponse;
import com.picpay.api.oauth2.infra.dto.request.DeviceRequest;
import com.picpay.api.oauth2.infra.dto.response.UserDeviceResponse;
import com.picpay.api.oauth2.infra.handler.exception.AlreadyExistsException;
import com.picpay.api.oauth2.infra.handler.exception.UsersNoContentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 06/10/2020
 */
@Api(tags = "Recebe uma solicitação de criação de um novo USER")
@RestController
@RequestMapping(value = "users", produces = "application/json; charset=UTF-8")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Uma lista de user paginados")
    @ApiResponses(value = {
        @ApiResponse(code = 302, message = "Found"),
        @ApiResponse(code = 204, message = "No Content", response = UsersNoContentException.class)
        //        @ApiResponse(code = 404, message = "Not Found", response = TokenUnauthorizedException.class),
        //        @ApiResponse(code = 404, message = "Not Found", response = UserResourceIdNotFoundException.class),
    })
    @GetMapping
    @ResponseStatus(FOUND)
    public List<UserResponse> findUserList(@RequestParam(value = "origin", required = false) final UserOrigin origin,
        @PageableDefault(sort = "createdAt", direction = DESC) final Pageable pages) {
        return userService.findUserList(origin, pages);
    }
    @GetMapping("/{id}/devices")
    @ResponseStatus(FOUND)
    public UserDeviceResponse findUser(@PathVariable(name = "id") final String consumerId) {
        return userService.findDeviceByConsumerId(consumerId);
    }

    @ApiOperation(value = "Gera um token se os dados de login e senha forem válidos", notes = "Endpoint usado por: <b>mobile</b>")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 422, message = "Unprocessable Entity", response = AlreadyExistsException.class),
    })
    @PostMapping
    @ResponseStatus(CREATED)
    public void createUser(@RequestBody @Valid final UserRequest userRequest,
        @RequestHeader(value = "origin") final UserOrigin origin) {
        userService.createUser(userRequest, origin);
    }
    @PutMapping("/{id}/devices")
    @ResponseStatus(OK)
    public void createUser(@PathVariable(name = "id") final String consumerId, @RequestBody @Valid final DeviceRequest userRequest) {
        userService.updateDevice(consumerId, userRequest);
    }

}
