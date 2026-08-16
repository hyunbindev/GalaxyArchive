package com.hyunbindev.article.article.port.event.outbound

import com.hyunbindev.article.article.data.ArticleViewEvent

interface ArticleViewEventPublishPort {
    fun publishViewEvent(event: ArticleViewEvent)
}