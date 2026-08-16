package com.hyunbindev.article.article.adapter.outbound

import com.hyunbindev.article.article.port.usecase.outbound.ArticleViewCountPersistencePort
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository

@Repository
internal class ArticleViewCountPersistenceAdapter(
    private val jdbcClient: JdbcClient,
): ArticleViewCountPersistencePort {
    override fun updateViewCountById(articleViewCount: Map<Long, Int>) {
        if(articleViewCount.isEmpty()) return

        val values = List(articleViewCount.keys.size)
        { index -> "(:articleId${index}, :delta${index})" }
            .joinToString(", ")

        val sql = """
            UPDATE article_stat As s
            SET view_count = s.view_count + v.delta
            FROM (VALUES ${values}) AS v(article_id, delta)
            WHERE s.article_id = v.article_id
        """.trimIndent()

        val statement = jdbcClient.sql(sql)

        articleViewCount.entries
            .forEachIndexed { index, (articleId, delta) ->
                statement
                    .param("articleId$index", articleId)
                    .param("delta$index", delta)
            }

        statement.update()
    }
}