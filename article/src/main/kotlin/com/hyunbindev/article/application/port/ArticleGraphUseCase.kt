package com.hyunbindev.article.application.port

import com.hyunbindev.article.domain.graph.VectorEdge

interface ArticleGraphUseCase {
    fun getAllArticleGraph(): List<VectorEdge>
}