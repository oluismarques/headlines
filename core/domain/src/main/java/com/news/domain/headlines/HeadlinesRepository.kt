package com.news.domain.headlines

import androidx.paging.PagingData
import com.news.util.Resource
import kotlinx.coroutines.flow.Flow

interface HeadlinesRepository {
    fun getTopHeadlines(source: String): Flow<PagingData<TopHeadlineItem>>
    fun getSources(): Flow<Resource<List<SourceItem>>>
}

