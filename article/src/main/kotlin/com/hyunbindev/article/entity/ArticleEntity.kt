package com.hyunbindev.article.entity

import com.hyunbindev.article.application.ArticleStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
class ArticleEntity(
    @Column(nullable = false)
    val title:String,

    @Column(nullable = false)
    val authorId: UUID,

    @Column(columnDefinition = "TEXT", nullable = false)
    val text:String,
    ) {
    @Id
    val id:Long? = null

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var isDeleted: Boolean = false

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: ArticleStatus = ArticleStatus.PENDING
}