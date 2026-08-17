package com.hyunbindev.article.article.application.service.query

import com.hyunbindev.article.article.port.usecase.inbound.ArticleQueryUseCase
import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.article.adapter.outbound.ArticleRepository
import com.hyunbindev.article.article.adapter.outbound.ArticleSummary
import com.hyunbindev.article.common.data.ArticleSummaryDto
import com.hyunbindev.article.common.data.ArticleSummaryPageDto
import com.hyunbindev.article.article.port.usecase.inbound.ArticleStatsQueryUseCase
import com.hyunbindev.article.comment.port.inbound.ArticleCommentQueryUseCase
import com.hyunbindev.article.article.adapter.outbound.ArticleKeywordRepository
import com.hyunbindev.article.article.data.ArticleViewEvent
import com.hyunbindev.article.article.port.event.outbound.ArticleViewEventPublishPort
import com.hyunbindev.article.article.port.usecase.outbound.ArticleViewCountPort
import com.hyunbindev.article.global.exception.ArticleException
import com.hyunbindev.article.global.exception.constant.ArticleExceptionCode
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
internal class ArticleQueryService(
    private val articleRepository: ArticleRepository,
    private val commentQueryUseCase: ArticleCommentQueryUseCase,
    private val articleKeywordRepository: ArticleKeywordRepository,
    private val articleViewCountPort: ArticleViewCountPort,
    private val articleViewEventPublishPort: ArticleViewEventPublishPort,
) : ArticleQueryUseCase, ArticleStatsQueryUseCase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun getArticle(id: Long, visitorId: UUID?, userId: UUID?): ArticleDto.Response {
        val article = articleRepository.findArticleById(id)
            ?: throw ArticleException(ArticleExceptionCode.ARTICLE_NOT_FOUND)

        val keywords = articleKeywordRepository.findAllByArticleOrderBySimilarityDesc(article)

        if (visitorId != null && article.id != null) {
            runCatching {
                val marked = articleViewCountPort.markIfFirstView(id, visitorId, userId)
                val event = ArticleViewEvent(
                    articleId = article.id,
                    userId = userId,
                    visitorId = visitorId,
                )
                if(marked) articleViewEventPublishPort.publishViewEvent(event)
            }.onFailure {
                logger.warn("Failed to record article view", it)
            }
        }

        return ArticleDto.Response.from(article, keywords)
    }

    override fun getArticleSummaryPageByCursorAndAuthor(
        authorId: UUID,
        cursorArticleId: Long?,
        size: Int,
        textLength: Int
    ): ArticleSummaryPageDto {
        val articleSummary: List<ArticleSummary> = articleRepository
            .findByArticleSummaryByUserIdByCursor(
                authorId = authorId,
                cursorId = cursorArticleId,
                size = size ?: (10 + 1),
                textLength = textLength ?: 100
            )

        val commentsCountMap: Map<Long, Int> = commentQueryUseCase
            .getCommentCountByArticleIds(articleSummary.map { it.id })

        val hasNextPage = articleSummary.size > size

        val articleIds = articleSummary.map { it.id }

        val keywordEntities = articleKeywordRepository.findAllByArticleIdInOrderBySimilarityDesc(articleIds)

        val keywordMap: Map<Long, List<String>> =
            keywordEntities.groupBy(keySelector = { it.article.id!! }, valueTransform = { it.keyword })

        val viewCountDelta:Map<Long,Long> = articleViewCountPort.getViewCountDeltas(articleIds.toList())

        val articleSummaryDtoList = articleSummary.take(size)
            .map { ArticleSummaryDto.of(
                projection = it,
                commentCount = commentsCountMap[it.id],
                keywords = keywordMap[it.id] ?: emptyList(),
                viewCount = (viewCountDelta[it.id] ?: 0L) + it.viewCount
            ) }

        return ArticleSummaryPageDto(
            articles = articleSummaryDtoList,
            size = articleSummaryDtoList.size,
            hasNextPage = hasNextPage,
            cursorArticleId = articleSummaryDtoList.lastOrNull()?.id
        )
    }

    override fun getArticleSummaryByIds(articleIds: List<Long>): List<ArticleSummaryDto> {

        val articleSummary: List<ArticleSummary> = articleRepository
            .findArticleByIdWithDeleted(articleIds, 100)

        val keywordEntities = articleKeywordRepository.findAllByArticleIdInOrderBySimilarityDesc(articleIds)

        val keywordMap: Map<Long, List<String>> =
            keywordEntities.groupBy(keySelector = { it.article.id!! }, valueTransform = { it.keyword })

        val commentsCountMap: Map<Long, Int> = commentQueryUseCase
            .getCommentCountByArticleIds(articleSummary.map { it.id })

        val viewCountDelta = articleViewCountPort.getViewCountDeltas(articleIds)

        return articleSummary
            .map {
                ArticleSummaryDto.of(
                    projection = it,
                    commentCount = commentsCountMap[it.id],
                    keywords = keywordMap[it.id] ?: emptyList(),
                    viewCount = (viewCountDelta[it.id] ?: 0L) + it.viewCount
                )
            }
    }

    override fun getArticleCountByAuthorId(authorId: UUID): Int = articleRepository.countByAuthorId(authorId)

    override fun isArticleExist(id: Long): Boolean = articleRepository.existsById(id)
}