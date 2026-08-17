package com.hyunbindev.article.trend.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "trending_article")
open class TrendingArticleEntity(

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val trendingArticleJob: TrendingArticleJob,

    @Column(nullable = false)
    val articleId: Long,

    @Column(nullable= false)
    val score: Float,

    @Column
    val x: Double? = null,

    @Column
    val y: Double? = null,

    @Column
    val z: Double? = null,
) {
    @Id
    val id: Long? = null;
}