package com.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.news.data.di.IoDispatcher
import com.news.data.network.model.sources.asDomainModel
import com.news.data.network.service.HeadlinesService
import com.news.data.paging.HeadlinesPagingSource
import com.news.domain.headlines.HeadlinesRepository
import com.news.domain.headlines.SourceItem
import com.news.domain.headlines.TopHeadlineItem
import com.news.util.Resource
import com.news.util.fetchNews
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


internal class HeadlinesRepositoryImp @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val headlinesService: HeadlinesService,
) : HeadlinesRepository {

    override fun getTopHeadlines(
        source: String,
    ): Flow<PagingData<TopHeadlineItem>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = {
            HeadlinesPagingSource(headlinesService, source)
        }
    ).flow

    override fun getSources(): Flow<Resource<List<SourceItem>>> {
        return fetchNews(ioDispatcher) { headlinesService.getSources().sourceItemsResponse.asDomainModel() }
    }
}
