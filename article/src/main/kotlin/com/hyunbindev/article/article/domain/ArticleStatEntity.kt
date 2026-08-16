package com.hyunbindev.article.article.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table


@Entity
@Table(name = "article_stat")
open class ArticleStatEntity(
    @Id
    var id:Long? = null,

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="article_id")
    val article:ArticleEntity,

    @Column(nullable = false)
    var viewCount:Long = 0,

    @Column(nullable = false)
    var commentCount:Long = 0
) {
    companion object {
        fun from(article:ArticleEntity):ArticleStatEntity = ArticleStatEntity(article=article)
    }
}