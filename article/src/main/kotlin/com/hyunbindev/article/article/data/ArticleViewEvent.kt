package com.hyunbindev.article.article.data

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID

data class ArticleViewEvent(
    val articleId:Long,
    val userId: UUID?,
    val visitorId: UUID?,
    @field:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss",
        timezone = "Asia/Seoul"
    )
    val viewedAt: Instant = Instant.now(),
)