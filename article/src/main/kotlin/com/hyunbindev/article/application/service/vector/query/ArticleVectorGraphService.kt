package com.hyunbindev.article.application.service.vector.query

import com.hyunbindev.article.domain.graph.VectorEdge
import com.hyunbindev.article.domain.repository.ArticleVectorRepository
import org.springframework.stereotype.Service

@Service
class ArticleVectorGraphService(
    private val articleVectorRepository: ArticleVectorRepository
) {
    private val parent = mutableMapOf<Long,Long>()


    fun getAllArticleGraph(edges:List<VectorEdge>):List<VectorEdge>{
        val sortedEdges = isSorted(edges)
        sortedEdges.asSequence()
            .forEach {  }

    }

    private fun unionFind(e: VectorEdge){

    }
    private fun find(n:Long): Long {
        parent[n] = parent[n] ?: n
        if (parent[n] == n) return n

        parent[n] = find(n)
        return parent[n]!!
    }
    private fun union(){

    }
    private fun isSorted(e:List<VectorEdge>):List<VectorEdge>{
        for (i in 0 until e.size-1){
            if(e[i] < e[i+1]) return e.sorted()
        }
        return e
    }
}