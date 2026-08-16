package com.hyunbindev.article.article.port.event.inbound

interface ArticleViewCountEventHandler {
    fun persistViewCountById(articleViewCount:Map<Long,Int>)
}