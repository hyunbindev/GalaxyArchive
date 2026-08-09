package com.hyunbindev.article.article.port.usecase.inbound

import com.hyunbindev.article.article.port.usecase.outbound.ArticleViewCountPort
import java.util.UUID

interface RecordArticleViewUseCase{
    fun recordArticleView(articleId:Long, visitorId: UUID, userId:UUID?)
}