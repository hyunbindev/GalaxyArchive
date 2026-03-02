package com.hyunbindev.user.application

import com.hyunbindev.user.application.signup.UserSignupService
import com.hyunbindev.user.data.UserInfoDto
import org.springframework.stereotype.Service

@Service
internal class UserServiceImpl(
    private val userSignupService: UserSignupService,
) : UserService {
    override fun signup(userInfoDto: UserInfoDto) {
        userSignupService.signup(userInfoDto)
    }

    override fun update(userInfoDto: UserInfoDto) {
        TODO("Not yet implemented")
    }

    override fun isMember(userProviderId: String):Boolean {
        TODO("Not yet implemented")
    }
}