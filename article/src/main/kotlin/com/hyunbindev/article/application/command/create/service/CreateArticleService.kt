package com.hyunbindev.article.application.command.create.service

import com.hyunbindev.article.data.dto.ArticleDto
import com.hyunbindev.article.domain.entity.ArticleEntity
import com.hyunbindev.article.domain.event.ArticleCreateEvent
import com.hyunbindev.article.domain.event.ArticleEventPublisher
import com.hyunbindev.article.domain.repository.ArticleRepository
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class CreateArticleService(
    private val articleRepository: ArticleRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val articleEventPublisher: ArticleEventPublisher
) {
    @Transactional
    fun createArticle(userId: UUID, req: ArticleDto.CreateRequest){
        val article: ArticleEntity = articleRepository.save(ArticleEntity.from(userId, req))
        articleEventPublisher.publishCreateEvent(ArticleCreateEvent.from(article))
    }
}