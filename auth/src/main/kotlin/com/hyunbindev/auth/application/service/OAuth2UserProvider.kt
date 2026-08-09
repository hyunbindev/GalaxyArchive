package com.hyunbindev.auth.application.service

import com.hyunbindev.auth.port.inbound.UserProviderUseCase
import com.hyunbindev.auth.exception.AuthException
import com.hyunbindev.auth.exception.constant.AuthExceptionCode
import com.hyunbindev.auth.oauth2.model.OAuth2UserPrincipal
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
internal class OAuth2UserProvider : UserProviderUseCase {

    private val logger = LoggerFactory.getLogger(OAuth2UserProvider::class.java)

    override fun getLoginUserId(): UUID {

        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw AuthException(AuthExceptionCode.USER_UNAUTHORIZED)

        //logger.debug(authentication.toString())

        val principal = authentication.principal as? OAuth2UserPrincipal
            ?: throw AuthException(AuthExceptionCode.OAUTH2_AUTHENTICATION_FAILED)

        return principal.userId
            ?: throw AuthException(AuthExceptionCode.USER_FORBIDDEN)
    }
}