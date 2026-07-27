package com.hyunbindev.article.comment.adapter.outbound

import com.hyunbindev.article.comment.domain.ArticleCommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ArticleCommentRepository : JpaRepository<ArticleCommentEntity, Long> {
    @Query("SELECT a FROM ArticleCommentEntity a WHERE a.id=:id AND a.isDeleted = false")
    fun findArticleCommentById(id: Long): ArticleCommentEntity?

    @Query("""
        SELECT c From ArticleCommentEntity c
        WHERE c.articleId = :articleId
        AND NOT (c.parent IS NOT NULL AND c.isDeleted = true)
        ORDER BY c.created DESC
        """)
    fun findCommentListByArticleId(articleId:Long): List<ArticleCommentEntity>

    @Query("""
        SELECT c.articleId AS articleId , count(c) AS commentCount FROM ArticleCommentEntity c
        WHERE c.articleId in :articleIds
        AND c.isDeleted = false
        GROUP BY c.articleId
    """)
    fun countCommentsByArticleIds(articleIds:List<Long>): List<CommentCountProjection>
}

interface CommentCountProjection {
    fun getArticleId(): Long
    fun getCommentCount(): Int
}