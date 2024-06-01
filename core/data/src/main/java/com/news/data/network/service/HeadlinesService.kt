package com.news.data.network.service

import com.news.data.network.model.TopHeadlinesResponse
import retrofit2.http.GET

internal interface HeadlinesService {

    @GET("top-headlines?country=us")
    suspend fun getTopHeadlines() : TopHeadlinesResponse


}