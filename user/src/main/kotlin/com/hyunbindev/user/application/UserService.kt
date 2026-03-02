package com.hyunbindev.user.application

import com.hyunbindev.user.data.UserInfoDto

interface UserService {
    fun signup(userInfoDto: UserInfoDto)
    fun update(userInfoDto: UserInfoDto)
    fun isMember(userProviderId:String):Boolean
}