package com.hyunbindev.infrastructure.article.redis

import com.hyunbindev.article.article.port.usecase.outbound.ArticleViewCountPort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.UUID

@Component
internal class ArticleViewCountRedisAdapter(
    private val redisTemplate: RedisTemplate<String, String>,
) : ArticleViewCountPort {

    companion object {
        private val VIEW_RECORD_TTL = Duration.ofDays(1)
        private val VIEW_COUNT_DELTA_TTL = Duration.ofDays(5)
    }

    override fun markIfFirstView(
        articleId: Long,
        visitorId: UUID,
        userId: UUID?,
    ): Boolean {
        return if (userId == null) {
            recordAnonymousView(
                articleId = articleId,
                visitorId = visitorId,
            )
        } else {
            recordUserView(
                articleId = articleId,
                visitorId = visitorId,
                userId = userId,
            )
        }
    }

    override fun getViewCountDelta(articleId: Long): Long {
        return redisTemplate.opsForValue()
            .get(viewCountDeltaKey(articleId))
            ?.toLongOrNull()
            ?: 0L
    }

    override fun getViewCountDeltas(
        articleIds: List<Long>,
    ): Map<Long, Long> {
        if (articleIds.isEmpty()) { return emptyMap() }

        val keys = articleIds.map(::viewCountDeltaKey)

        val values = redisTemplate.opsForValue()
            .multiGet(keys)
            .orEmpty()

        return articleIds.zip(values)
            .associate { (articleId, value) ->
                articleId to (value?.toLongOrNull() ?: 0L)
            }
    }

    override fun deleteViewCountDeltas(articleId: List<Long>) {
        if(articleId.isEmpty()) return

        val keys = articleId.map(::viewCountDeltaKey)

        redisTemplate.delete(keys)
    }


    private fun recordAnonymousView(
        articleId: Long,
        visitorId: UUID,
    ): Boolean {
        val visitorKey = visitorViewKey(
            articleId = articleId,
            visitorId = visitorId,
        )

        val firstView = redisTemplate.opsForValue()
            .setIfAbsent(
                visitorKey,
                "1",
                VIEW_RECORD_TTL,
            )
            ?: false

        if (firstView) { incrementViewCountDelta(articleId) }

        return firstView
    }

    private fun recordUserView(
        articleId: Long,
        visitorId: UUID,
        userId: UUID,
    ): Boolean {
        val visitorKey = visitorViewKey(
            articleId = articleId,
            visitorId = visitorId,
        )

        val userKey = userViewKey(
            articleId = articleId,
            userId = userId,
        )

        val isVisitorViewed =
            redisTemplate.hasKey(visitorKey) ?: false

        val isUserViewed =
            redisTemplate.hasKey(userKey) ?: false

        if (isVisitorViewed || isUserViewed) {

            // 다른 브라우저에서 동일 user로 접근
            if (!isVisitorViewed) {
                redisTemplate.opsForValue()
                    .set(
                        visitorKey,
                        "1",
                        VIEW_RECORD_TTL,
                    )
            }

            // 비로그인으로 본 뒤 로그인
            if (!isUserViewed) {
                redisTemplate.opsForValue()
                    .set(
                        userKey,
                        "1",
                        VIEW_RECORD_TTL,
                    )
            }

            return false
        }

        redisTemplate.opsForValue()
            .set(
                visitorKey,
                "1",
                VIEW_RECORD_TTL,
            )

        redisTemplate.opsForValue()
            .set(
                userKey,
                "1",
                VIEW_RECORD_TTL,
            )

        incrementViewCountDelta(articleId)

        return true
    }

    private fun incrementViewCountDelta(
        articleId: Long,
    ) {
        val key = viewCountDeltaKey(articleId)

        redisTemplate.opsForValue()
            .increment(key)

        redisTemplate.expire(
            key,
            VIEW_COUNT_DELTA_TTL,
        )
    }

    private fun userViewKey(
        articleId: Long,
        userId: UUID,
    ): String =
        "article:view:$articleId:u:$userId"

    private fun visitorViewKey(
        articleId: Long,
        visitorId: UUID,
    ): String =
        "article:view:$articleId:v:$visitorId"

    private fun viewCountDeltaKey(
        articleId: Long,
    ): String =
        "article:view:delta:$articleId"
}