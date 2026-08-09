package com.hyunbindev.article.article.application.service.command.create

import com.hyunbindev.article.article.port.usecase.inbound.RecordArticleViewUseCase
import com.hyunbindev.article.article.port.usecase.outbound.ArticleViewCountPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class RecordArticleStatService(
    private val articleViewCountPort: ArticleViewCountPort
): RecordArticleViewUseCase {
    override fun recordArticleView(articleId: Long, visitorId: UUID, userId: UUID?) {
        articleViewCountPort.markIfFirstView(articleId, visitorId, userId)
    }
}