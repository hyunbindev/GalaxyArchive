package com.hyunbindev.user.application.service.command.signup

import com.hyunbindev.user.application.port.UserSignupUseCase
import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.entity.UserEntity
import com.hyunbindev.user.repository.UserRepository

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class UserSignupService(
    private val userRepository: UserRepository,
): UserSignupUseCase {
    @Transactional
    override fun signup(userInfoDto: UserInfoDto): UserInfoDto {
        val userEntity: UserEntity = UserEntity.from(userInfoDto)
        userRepository.save(userEntity)
        return UserInfoDto.from(userEntity)
    }
}