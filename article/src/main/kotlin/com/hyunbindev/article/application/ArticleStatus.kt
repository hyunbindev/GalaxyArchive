package com.hyunbindev.article.application

enum class ArticleStatus(val status:String) {
    DRAFT("DRAFT"),
    PENDING("PENDING"),
    CALCULATING("CALCULATING"),
    COMPLETED("COMPLETED"),
}