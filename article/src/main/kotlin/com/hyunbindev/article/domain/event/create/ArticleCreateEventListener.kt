package com.hyunbindev.article.domain.event.create

interface ArticleCreateEventListener {
    fun onArticleCreated(event: ArticleCreateEvent)
}