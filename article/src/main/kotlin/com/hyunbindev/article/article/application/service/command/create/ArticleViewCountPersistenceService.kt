package com.hyunbindev.article.article.application.service.command.create

import com.hyunbindev.article.article.port.event.inbound.ArticleViewCountEventHandler
import com.hyunbindev.article.article.port.usecase.outbound.ArticleViewCountPersistencePort
import com.hyunbindev.article.article.port.usecase.outbound.ArticleViewCountPort

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
class ArticleViewCountPersistenceService(
    private val articleViewCountPort: ArticleViewCountPort,
    private val articleViewCountPersistencePort: ArticleViewCountPersistencePort,
):ArticleViewCountEventHandler {
    @Transactional
    override fun persistViewCountById(articleViewCount: Map<Long, Int>) {
        articleViewCountPersistencePort.updateViewCountById(articleViewCount)

        TransactionSynchronizationManager.registerSynchronization(
            object : TransactionSynchronization {
                override fun afterCommit() {
                    val articleIds = articleViewCount.keys.toList()
                    articleViewCountPort.deleteViewCountDeltas(articleIds)
                }
            }
        )
    }
}