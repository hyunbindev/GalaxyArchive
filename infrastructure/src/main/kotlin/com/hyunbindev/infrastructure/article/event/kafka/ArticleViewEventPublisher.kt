package com.hyunbindev.infrastructure.article.event.kafka

import com.hyunbindev.article.article.data.ArticleCreateEvent
import com.hyunbindev.article.article.data.ArticleViewEvent
import com.hyunbindev.article.article.port.event.outbound.ArticleViewEventPublishPort
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class ArticleViewEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, ArticleViewEvent>
): ArticleViewEventPublishPort {
    private val logger = LoggerFactory.getLogger(ArticleViewEventPublisher::class.java)
    override fun publishViewEvent(event: ArticleViewEvent) {
        try {
            val response = kafkaTemplate.send("article-viewed", event.articleId.toString(), event)
            logger.debug("Published article view event: {}", response.toString())
            response.get(3, TimeUnit.SECONDS)
        }catch (e: Exception){
            //TODO-재시도 예외처리
            logger.error("Error in publishing article view event", e)
        }
    }
}