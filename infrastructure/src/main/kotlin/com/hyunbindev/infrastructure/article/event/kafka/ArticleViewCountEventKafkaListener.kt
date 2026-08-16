package com.hyunbindev.infrastructure.article.event.kafka

import com.hyunbindev.article.article.data.ArticleViewEvent
import com.hyunbindev.article.article.port.event.inbound.ArticleViewCountEventHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ArticleViewCountEventKafkaListener(
    private val articleViewCountEventHandler: ArticleViewCountEventHandler,
) {
    @KafkaListener(
        topics = ["article-viewed"],
        groupId = "article-view-count-batch",
        batch = "true",
    )
    fun getArticleViewEvent(events: List<ArticleViewEvent>) {
        val viewCounts = events.groupingBy { it.articleId }.eachCount()
        articleViewCountEventHandler.persistViewCountById(viewCounts)
    }
}