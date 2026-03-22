package com.hyunbindev.user.application.port

import com.hyunbindev.user.data.UserInfoDto

interface UserSignupUseCase {
    fun signup(userInfoDto: UserInfoDto):UserInfoDto
}