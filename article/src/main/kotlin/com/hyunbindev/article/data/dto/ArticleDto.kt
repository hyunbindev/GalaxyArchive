package com.hyunbindev.article.data.dto

import com.hyunbindev.article.domain.entity.ArticleEntity
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

class ArticleDto {
    @Schema(description = "request article")
    data class CreateRequest(
        val title: String,
        val text: String,
    )

    @Schema(description = "response article")
    data class Response(
        val title: String,
        val text: String,
        val authorId: UUID,
        val createdAt: LocalDateTime
    ){
        companion object{
            fun from(entity: ArticleEntity): Response{
                return Response(
                    title = entity.title,
                    text = entity.text,
                    authorId = entity.authorId,
                    createdAt = entity.createdAt
                )
            }
        }
    }
}