package com.hyunbindev.infrastructure.article.event.kafka.publisher

import com.hyunbindev.article.trend.data.TrendingArticleCalculateEvent
import com.hyunbindev.article.trend.port.outbound.TrendingArticleCalculatedEventPublisher
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class TrendingArticleCalculatedKafkaPublisher(
    private val kafkaTemplate: KafkaTemplate<String, TrendingArticleCalculateEvent>
): TrendingArticleCalculatedEventPublisher {
    private val logger = LoggerFactory.getLogger(TrendingArticleCalculatedKafkaPublisher::class.java)
    override fun publish(event: TrendingArticleCalculateEvent) {
        try{
            kafkaTemplate.send(
                "trending-article-calculated",
                event.traceId.toString(),
                event )
        }catch(ex: Exception){
            logger.error(ex.message, ex)
        }
    }
}