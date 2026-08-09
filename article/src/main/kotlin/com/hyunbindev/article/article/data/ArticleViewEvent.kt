package com.hyunbindev.article.article.data

import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

data class ArticleViewEvent(
    val articleId:Long,
    val userId: UUID?,
    val visitorId: UUID?,
    val viewedAt: Instant = Instant.now(),
)