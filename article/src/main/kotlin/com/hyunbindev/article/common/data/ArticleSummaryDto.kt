package com.hyunbindev.article.common.data

import com.hyunbindev.article.article.adapter.outbound.ArticleSummary
import java.time.LocalDateTime



data class ArticleSummaryDto(
    val id:Long,
    val title:String,
    val description:String,
    val createdAt: LocalDateTime,
    val commentsCount:Int=0,
    val keywords:List<String>,
    val thumbnailUrl:String?,
    val viewCount:Long=0,
){
    companion object{
        fun of(projection: ArticleSummary,
               commentCount:Int?,
               keywords:List<String>,
               viewCount:Long
        ):ArticleSummaryDto{
            return ArticleSummaryDto(
                requireNotNull(projection.id) { "article entity is not persistent" },
                title = projection.title,
                description = projection.text,
                createdAt = projection.createdAt,
                commentsCount = commentCount?:0,
                keywords = keywords,
                thumbnailUrl = projection.thumbnailUrl,
                viewCount = viewCount?:0,
            )
        }
    }
}