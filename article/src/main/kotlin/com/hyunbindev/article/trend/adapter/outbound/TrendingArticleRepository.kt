package com.hyunbindev.article.trend.adapter.outbound

import com.hyunbindev.article.trend.domain.TrendingArticleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TrendingArticleRepository : JpaRepository<TrendingArticleEntity, Long> {
}