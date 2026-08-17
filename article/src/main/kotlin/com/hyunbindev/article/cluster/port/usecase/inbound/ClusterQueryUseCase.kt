package com.hyunbindev.article.cluster.port.usecase.inbound

import com.hyunbindev.article.common.data.ArticleSummaryDto
import com.hyunbindev.article.cluster.domain.UserClusterSnapShot
import java.util.UUID

interface ClusterQueryUseCase {
    fun getUserRecentCompletedCluster(userId: UUID): UserClusterSnapShot
    fun getArticleInCluster(clusterId:Long): List<ArticleSummaryDto>
}