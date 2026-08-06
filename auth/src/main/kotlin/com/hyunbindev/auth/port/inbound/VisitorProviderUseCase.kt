package com.hyunbindev.auth.port.inbound

import java.util.UUID

interface VisitorProviderUseCase {
    fun issueVisitorId(): UUID
}