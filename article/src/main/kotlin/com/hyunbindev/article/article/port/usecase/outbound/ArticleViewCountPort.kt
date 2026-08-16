package com.hyunbindev.article.article.port.usecase.outbound

import java.util.UUID

interface ArticleViewCountPort {

    fun markIfFirstView(
        articleId: Long,
        visitorId: UUID,
        userId: UUID?,
    ): Boolean

    fun getViewCountDelta(articleId: Long): Long

    fun getViewCountDeltas(
        articleIds: List<Long>,
    ): Map<Long, Long>

    fun deleteViewCountDeltas(articleId: List<Long>)
}