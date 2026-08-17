package com.hyunbindev.article.article.port.usecase.inbound

import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.common.data.ArticleSummaryDto
import com.hyunbindev.article.common.data.ArticleSummaryPageDto
import java.util.UUID

interface ArticleQueryUseCase {
    fun getArticle(id:Long, visitorId:UUID?, userId:UUID?): ArticleDto.Response
    fun isArticleExist(id:Long):Boolean
    fun getArticleSummaryPageByCursorAndAuthor(authorId: UUID, cursorArticleId:Long?, size:Int, textLength:Int): ArticleSummaryPageDto
    fun getArticleSummaryByIds(articleIds: List<Long>): List<ArticleSummaryDto>
}