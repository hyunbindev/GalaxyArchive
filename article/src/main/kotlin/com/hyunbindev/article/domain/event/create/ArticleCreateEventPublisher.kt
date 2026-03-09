package com.hyunbindev.article.domain.event.create

interface ArticleCreateEventPublisher {
    fun publishCreateEvent(event:ArticleCreateEvent)
}