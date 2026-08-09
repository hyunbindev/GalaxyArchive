package com.hyunbindev.article.article.port.usecase.inbound

interface RecordArticleCommentUseCase {
    fun incrementCount(articleId:Long):Long

    fun decrementCount(articleId:Long):Long
}