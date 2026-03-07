package com.hyunbindev.article.domain.event

interface ArticleEventPublisher {
    fun publishCreateEvent(event:ArticleCreateEvent)
}