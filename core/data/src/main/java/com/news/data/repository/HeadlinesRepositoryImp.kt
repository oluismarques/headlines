package com.news.data.repository

import com.news.data.di.IoDispatcher
import com.news.data.network.model.asDomainModel
import com.news.data.network.service.HeadlinesService
import com.news.domain.headlines.HeadlinesRepository
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
    override fun getTopHeadlines(): Flow<Resource<List<TopHeadlineItem>>> {
        return fetchNews(ioDispatcher) { headlinesService.getTopHeadlines().contentHeadlineResponse.asDomainModel() }
    }
}




