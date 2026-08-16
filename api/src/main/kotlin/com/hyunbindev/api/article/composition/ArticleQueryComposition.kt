package com.hyunbindev.api.article.composition

import com.hyunbindev.api.article.data.ArticleCompositionResponse
import com.hyunbindev.article.article.port.usecase.inbound.ArticleQueryUseCase
import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.common.auth.VisitorId
import com.hyunbindev.user.data.UserProfileDto
import com.hyunbindev.user.port.usecase.inbound.UserProfileQueryUseCase
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ArticleQueryComposition(
    private val articleQueryUseCase: ArticleQueryUseCase,
    private val userProfileQueryUseCase: UserProfileQueryUseCase
) {
    fun getArticle(articleId: Long, visitorId: UUID?, userId:UUID?):ArticleCompositionResponse {
        val articleDto: ArticleDto.Response = articleQueryUseCase.getArticle(articleId, visitorId, userId)
        val authorDto: UserProfileDto = userProfileQueryUseCase.getUserProfile(articleDto.authorId)

        return ArticleCompositionResponse.of(articleDto,authorDto)
    }
}