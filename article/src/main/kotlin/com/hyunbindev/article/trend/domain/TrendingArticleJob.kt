package com.hyunbindev.article.trend.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import java.time.LocalDateTime

@Entity
@Table(name="trending_article_job")
open class TrendingArticleJob(

    @Enumerated(EnumType.STRING)
    @Column
    var status:TrendingJobStatus = TrendingJobStatus.PENDING,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null

    @CreatedDate
    val createdAt: Instant = Instant.now()

    fun run(){
        this.status = TrendingJobStatus.RUNNING
    }

    fun done(){
        this.status = TrendingJobStatus.DONE
    }

    fun fail(){
        this.status = TrendingJobStatus.FAILED
    }
}

enum class TrendingJobStatus(var status: String){
    PENDING("PENDING"),
    RUNNING("RUNNING"),
    DONE("DONE"),
    FAILED("FAILED")
}