package com.hyunbindev.article.article.port.usecase.outbound

interface ArticleViewCountPersistencePort {
    fun updateViewCountById(articleViewCount: Map<Long, Int>)
}