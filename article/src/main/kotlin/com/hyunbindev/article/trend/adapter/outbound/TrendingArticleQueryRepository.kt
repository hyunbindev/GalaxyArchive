package com.hyunbindev.article.trend.adapter.outbound

import com.hyunbindev.article.article.domain.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface TrendingArticleQueryRepository : JpaRepository<ArticleEntity, Long> {

    @Query(
        value = """
            SELECT
                article.id AS articleId,
                (
                    1.5 * LN(1 + COALESCE(stat.view_count, 0))
                    +
                    2.0 * LN(1 + COALESCE(stat.comment_count, 0))
                )
                *
                EXP(
                    -EXTRACT(
                        EPOCH FROM (NOW() - article.created_at)
                    ) / 3600.0 / 72.0
                ) AS score
            FROM article
            JOIN article_stat stat
                ON article.id = stat.article_id
            ORDER BY score DESC
            LIMIT :count
        """,
        nativeQuery = true,
    )
    fun findTrendingCandidate(count:Int):List<ArticleTrendingScore>
}

interface ArticleTrendingScore{
    val articleId: Long;
    val score: Float;
}