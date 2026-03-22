package com.hyunbindev.user.application.port

import com.hyunbindev.user.data.UserInfoDto

interface UserUpdateUseCase {
    fun update(userInfoDto: UserInfoDto):UserInfoDto
}