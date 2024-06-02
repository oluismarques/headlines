package com.news.domain.headlines

import com.news.util.Resource
import kotlinx.coroutines.flow.Flow

interface HeadlinesRepository {
    fun getTopHeadlines(): Flow<Resource<List<TopHeadlineItem>>>


}

