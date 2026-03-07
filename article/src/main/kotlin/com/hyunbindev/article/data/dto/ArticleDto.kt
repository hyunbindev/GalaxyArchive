package com.hyunbindev.article.data.dto

class ArticleDto {
    data class CreateRequest(
        val title: String,
        val text: String,
    )
}