package com.hyunbindev.article.repository

import com.hyunbindev.article.entity.ArticleVectorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleVectorRepository : JpaRepository<ArticleVectorEntity, Long> {
}