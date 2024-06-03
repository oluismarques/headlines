package com.news.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.news.data.network.model.headlines.asDomainModel
import com.news.data.network.service.HeadlinesService
import com.news.domain.headlines.TopHeadlineItem
import retrofit2.HttpException
import java.io.IOException


internal class HeadlinesPagingSource(
    private val headlineApi: HeadlinesService,
    private val source: String,
) : PagingSource<Int, TopHeadlineItem>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopHeadlineItem> {
        val position = params.key ?: 1
        return try {
            val data = headlineApi.getTopHeadlines(
                source = source,
                page = position,
                pageSize = 6
            )
            val nextKey = if (data.contentHeadlineResponse.isEmpty()) {
                null
            } else {
                position + 1
            }
            val prevKey = if (position == 1) null else position - 1
            LoadResult.Page(
                data = data.contentHeadlineResponse.asDomainModel(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TopHeadlineItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}