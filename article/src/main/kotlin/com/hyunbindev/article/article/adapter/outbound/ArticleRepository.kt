package com.hyunbindev.article.article.adapter.outbound

import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.article.domain.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface ArticleRepository : JpaRepository<ArticleEntity, Long> {
    @Query("SELECT a FROM ArticleEntity a WHERE a.id=:id AND a.isDeleted = false")
    fun findArticleById(id: Long): ArticleEntity?

    @Query("SELECT a FROM ArticleEntity a WHERE a.id=:id AND a.isDeleted = true")
    fun findArticleByIdWithDeleted(id: Long): ArticleEntity?

    @Query("""
        SELECT
            article.id AS id,
            article.title AS title,
            LEFT(COALESCE(article.raw_text,''), :textLength) AS text,
            article.created_at AS createdAt,
            article.author_id AS authorId
        FROM article article 
        WHERE article.id IN :ids 
        AND article.is_deleted = false
        ORDER BY article.created_at DESC
    """, nativeQuery = true)
    fun findArticleByIdWithDeleted(ids: List<Long>, textLength: Int): List<ArticleSummary>

    @Query(
        value = """
        SELECT
         article.id AS id,
         article.title AS title,
         LEFT(COALESCE(article.raw_text,''), :textLength) AS text,
         article.created_at AS createdAt,
         article.author_id AS authorId,
         image.raw_key AS thumbnailUrl
        FROM article
        LEFT JOIN LATERAL (
            SELECT raw_key
            FROM article_image image
            WHERE image.article_id = article.id
            ORDER BY image.id
            LIMIT 1
        ) image ON true
        WHERE article.author_id = :authorId
        AND article.is_deleted = false
        AND (:cursorId IS NULL OR article.id < :cursorId)
        ORDER BY article.created_at DESC
        LIMIT :size
    """, nativeQuery = true
    )
    fun findByArticleSummaryByUserIdByCursor(
        size: Int,
        cursorId: Long?,
        authorId: UUID,
        textLength: Int
    ): List<ArticleSummary>

    @Query("""
        SELECT count(article) FROM ArticleEntity article 
        WHERE article.authorId=:authorId
        AND article.isDeleted = false
    """)
    fun countByAuthorId(authorId: UUID): Int
}

interface ArticleSummary {
    val id: Long
    val title: String
    val text: String
    val createdAt: LocalDateTime
    val authorId: UUID
    val thumbnailUrl:String?
}