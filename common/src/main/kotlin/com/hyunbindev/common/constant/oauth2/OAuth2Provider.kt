package com.hyunbindev.common.constant.oauth2

enum class OAuth2Provider(
    val registrationId: String
) {
    GITHUB("github"),
    GOOGLE("google");

    companion object{
        fun from(registrationId: String) : OAuth2Provider {
            return OAuth2Provider.entries.find { it.registrationId == registrationId } ?: throw IllegalArgumentException("Provider not found")
        }
    }
}