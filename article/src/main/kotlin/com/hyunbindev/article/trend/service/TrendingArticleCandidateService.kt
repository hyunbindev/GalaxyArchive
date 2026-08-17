package com.hyunbindev.article.trend.service

import com.hyunbindev.article.trend.adapter.outbound.ArticleTrendingScore
import com.hyunbindev.article.trend.adapter.outbound.TrendingArticleJobRepository
import com.hyunbindev.article.trend.adapter.outbound.TrendingArticleQueryRepository
import com.hyunbindev.article.trend.adapter.outbound.TrendingArticleRepository
import com.hyunbindev.article.trend.data.TrendingArticleCalculateEvent
import com.hyunbindev.article.trend.domain.TrendingArticleEntity
import com.hyunbindev.article.trend.domain.TrendingArticleJob
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class TrendingArticleCandidateService(
    private val trendingArticleRepository: TrendingArticleRepository,
    private val trendingArticleJobRepository: TrendingArticleJobRepository,
    private val trendingArticleQueryRepository: TrendingArticleQueryRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    private val logger = LoggerFactory.getLogger(TrendingArticleCandidateService::class.java)
    @Transactional
    fun calculateTrendingArticle(count:Int=50){
        val trendingArticleJob = trendingArticleJobRepository
            .save(TrendingArticleJob())

        val articleTrendingScores:List<ArticleTrendingScore> = trendingArticleQueryRepository
            .findTrendingCandidate(count)

        val trendingArticles:List<TrendingArticleEntity> = articleTrendingScores
            .map { TrendingArticleEntity(
                trendingArticleJob = trendingArticleJob,
                articleId = it.articleId,
                score = it.score
            ) }

        trendingArticleRepository.saveAll(trendingArticles)

        if(trendingArticleJob.id == null){
            logger.error("Fail to generate trending article job")
            throw IllegalStateException("Fail to generate trending article job")
        }

        applicationEventPublisher.publishEvent(TrendingArticleCalculateEvent(trendingArticleJob.id))
    }
}