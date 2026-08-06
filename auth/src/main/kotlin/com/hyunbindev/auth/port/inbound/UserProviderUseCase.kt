package com.hyunbindev.auth.port.inbound

import java.util.UUID

interface UserProviderUseCase {
    fun getLoginUserId(): UUID?
}