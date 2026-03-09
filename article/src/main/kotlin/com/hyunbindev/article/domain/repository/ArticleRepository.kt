package com.hyunbindev.article.domain.repository

import com.hyunbindev.article.domain.entity.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ArticleRepository : JpaRepository<ArticleEntity, Long> {
}