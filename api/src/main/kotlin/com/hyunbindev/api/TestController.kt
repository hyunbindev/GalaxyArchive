package com.hyunbindev.api

import com.hyunbindev.common.auth.LoginUserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/test")
class TestController {
    @GetMapping
    fun test(): String = "test controller"

    @GetMapping("/public/authentication")
    fun authenticationEndPoint(@LoginUserId loginUserId: UUID) = "isAuthenticated"
}