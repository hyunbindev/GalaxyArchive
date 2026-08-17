package com.hyunbindev.article.common.data

data class ArticleSummaryPageDto(
    val articles: List<ArticleSummaryDto>,
    val size:Int,
    val hasNextPage: Boolean,
    val cursorArticleId:Long?,
)
