package com.hyunbindev.article.application

import com.hyunbindev.article.data.dto.ArticleDto

interface ArticleApplication {
    fun createArticle(req: ArticleDto.CreateRequest)

    fun getArticle()

    fun updateArticle()

    fun deleteArticle()
}