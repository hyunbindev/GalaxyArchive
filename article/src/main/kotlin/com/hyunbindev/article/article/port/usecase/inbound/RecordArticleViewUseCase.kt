package com.hyunbindev.article.article.port.usecase.inbound

import java.util.UUID

interface RecordArticleViewUseCase{
    fun recordArticleView(articleId:Long, visitorId: UUID, userId:UUID?)
}