package com.hyunbindev.article.application.create.service

import com.hyunbindev.article.data.dto.ArticleDto
import com.hyunbindev.article.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
internal class CreateArticleService(
    private val articleRepository: ArticleRepository
) {
    fun createArticle(req: ArticleDto.CreateRequest){

    }
}