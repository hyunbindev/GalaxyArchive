package com.hyunbindev.article.infrastructure

import com.hyunbindev.article.domain.event.ArticleCreateEvent
import com.hyunbindev.article.domain.event.ArticleEventPublisher
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ArticleKafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
): ArticleEventPublisher {
    private val logger = LoggerFactory.getLogger(ArticleKafkaEventPublisher::class.java)
    override fun publishCreateEvent(event: ArticleCreateEvent) {
        try{
            kafkaTemplate.send("article-events", event.articleId.toString(), event)
        }catch (e:Exception){
            logger.error("[Fail to kafka event] : ArticleId : [{}] : TraceId : [{}] : {}", event.articleId , event.traceId, e.message ,e)
            throw e
        }
    }
}