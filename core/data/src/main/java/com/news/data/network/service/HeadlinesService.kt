package com.news.data.network.service

import com.news.data.network.model.headlines.TopHeadlinesResponse
import com.news.data.network.model.sources.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface HeadlinesService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") source: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): TopHeadlinesResponse


    @GET("top-headlines/sources")
    suspend fun getSources(): SourceResponse
}