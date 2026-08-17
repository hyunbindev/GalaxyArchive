package com.hyunbindev.article.trend.data

import java.util.UUID

data class TrendingArticleCalculateEvent(
    val jobId:Long,
    val traceId:UUID = UUID.randomUUID()
)
