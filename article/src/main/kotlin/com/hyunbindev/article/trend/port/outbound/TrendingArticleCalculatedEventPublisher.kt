package com.hyunbindev.article.trend.port.outbound

import com.hyunbindev.article.trend.data.TrendingArticleCalculateEvent

interface TrendingArticleCalculatedEventPublisher {
    fun publish(event: TrendingArticleCalculateEvent)
}