package com.news.domain.headlines

import androidx.paging.PagingData
import com.news.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val mockSources = listOf(
    SourceItem(
        id = "adipisci", name = "Meagan Tanner"
    )
)

class FakeHeadlinesRepository : HeadlinesRepository {

    private var shouldFail: Boolean = false

    fun setShouldFail(shouldFail: Boolean) {
        this.shouldFail = shouldFail
    }


    override fun getTopHeadlines(source: String): Flow<PagingData<TopHeadlineItem>> {
        return flow {
            emit(PagingData.empty())
        }
    }

    override fun getSources(): Flow<Resource<List<SourceItem>>> = flow{
        emit(Resource.Loading)

        if (shouldFail) {
            emit(Resource.Error("Failed to fetch movie detail"))
        } else {
            emit(Resource.Success(mockSources))
        }
    }


}
