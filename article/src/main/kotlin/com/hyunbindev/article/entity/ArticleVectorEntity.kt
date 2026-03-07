package com.hyunbindev.article.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class ArticleVectorEntity(
    @Id
    val articleId: Long? = null,

    @Column(columnDefinition = "vector(15000)")
    @JdbcTypeCode(SqlTypes.ARRAY)
    val embedding: DoubleArray,
) {
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    lateinit var article: ArticleEntity

    @LastModifiedDate
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
}