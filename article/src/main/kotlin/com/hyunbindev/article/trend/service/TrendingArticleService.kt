package com.hyunbindev.article.trend.service


import com.hyunbindev.article.trend.data.TrendingArticleCalculateEvent
import com.hyunbindev.article.trend.port.outbound.TrendingArticleCalculatedEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
internal class TrendingArticleService(
    private val trendingArticleQueryService:TrendingArticleCandidateService,
    private val trendingArticleCalculatedEventPublisher: TrendingArticleCalculatedEventPublisher
) {

    fun calculateTrendingArticle(){
        trendingArticleQueryService.calculateTrendingArticle()
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private fun afterCommitCalculateTrendingArticleJob(event: TrendingArticleCalculateEvent){
        trendingArticleCalculatedEventPublisher.publish(event)
    }
}