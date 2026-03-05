package com.hyunbindev.api.controller

import com.hyunbindev.common.auth.LoginUserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/test/auth")
class TestAuthController {
    @GetMapping
    fun test(@LoginUserId userUuid: UUID): String = "test authenticated controller injected by resolver user id is ${userUuid.toString()}"
}