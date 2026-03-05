package com.hyunbindev.common.auth

import java.util.UUID

interface UserProvider {
    fun getLoginUserId(): UUID
}