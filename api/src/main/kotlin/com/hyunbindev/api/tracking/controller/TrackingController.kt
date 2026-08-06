package com.hyunbindev.api.tracking.controller

import com.hyunbindev.auth.port.inbound.VisitorProviderUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal/api/v1/tracking")
class TrackingController(
    private val visitorProvider: VisitorProviderUseCase
) {
    @PostMapping("/identity")
    fun getIdentity():String {
        return visitorProvider.issueVisitorId().toString()
    }
}