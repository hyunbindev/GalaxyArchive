package com.hyunbindev.article.article.adapter.outbound

import com.hyunbindev.article.article.domain.ArticleStatEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleStatRepository: JpaRepository<ArticleStatEntity, Long> {
}