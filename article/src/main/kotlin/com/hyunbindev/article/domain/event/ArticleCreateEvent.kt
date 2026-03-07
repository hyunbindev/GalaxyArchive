package com.hyunbindev.article.domain.event

import com.hyunbindev.article.domain.entity.ArticleEntity
import java.time.LocalDateTime
import java.util.UUID

data class ArticleCreateEvent(
    val articleId:Long,
    val authorId: UUID,
    val traceId:String = UUID.randomUUID().toString(),
    val occuredAt: LocalDateTime = LocalDateTime.now()
){
    companion object{
        fun from(article: ArticleEntity): ArticleCreateEvent{
            val id = article.id ?: throw IllegalArgumentException("article id is null")
            return ArticleCreateEvent(
                articleId = id,
                authorId = article.authorId,
            )
        }
    }
}
