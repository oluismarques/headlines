package com.news.data.network.service.fake

import com.news.data.network.fake.FakeAssetManager
import com.news.data.network.model.headlines.TopHeadlinesResponse
import com.news.data.network.model.sources.SourceResponse
import com.news.data.network.service.HeadlinesService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.IOException
import javax.inject.Inject

internal class FakeHeadlinesService @Inject constructor(
    private val networkJson: Json,
    private val assets: FakeAssetManager,
) : HeadlinesService {

    private var shouldFail: Boolean = false

    fun setShouldFail(shouldFail: Boolean) {
        this.shouldFail = shouldFail
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getTopHeadlines(
        source: String,
        page: Int,
        pageSize: Int,
    ): TopHeadlinesResponse {
        if (shouldFail) {
            throw IOException("Simulated network error")
        }
        return assets.open(HEADLINES_ASSET).use(networkJson::decodeFromStream)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getSources(): SourceResponse {
        if (shouldFail) {
            throw RuntimeException("Simulated network error")
        }
        return assets.open(SOURCES_ASSET).use(networkJson::decodeFromStream)
    }

    companion object {
        private const val SOURCES_ASSET = "sources.json"
        private const val HEADLINES_ASSET = "headlines.json"
    }
}
