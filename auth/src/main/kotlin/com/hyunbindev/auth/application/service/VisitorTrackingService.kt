package com.hyunbindev.auth.application.service

import com.github.f4b6a3.uuid.UuidCreator
import com.hyunbindev.auth.port.inbound.VisitorProviderUseCase
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class VisitorTrackingService(): VisitorProviderUseCase {

    override fun issueVisitorId(): UUID {
        return UuidCreator.getTimeOrderedEpoch()
    }

}