package com.hyunbindev.article.trend.adapter.outbound

import com.hyunbindev.article.trend.domain.TrendingArticleJob
import org.springframework.data.jpa.repository.JpaRepository

interface TrendingArticleJobRepository : JpaRepository<TrendingArticleJob, Long> {
}