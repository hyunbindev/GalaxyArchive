package com.hyunbindev.article.domain.repository

import com.hyunbindev.article.domain.entity.ArticleVectorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleVectorRepository : JpaRepository<ArticleVectorEntity, Long> {
}