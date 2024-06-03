package com.news.launchpad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.news.domain.headlines.HeadlinesRepository
import com.news.domain.headlines.SourceItem
import com.news.domain.headlines.TopHeadlineItem
import com.news.util.FlavorChecker
import com.news.util.Resource
import com.news.util.asMutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LaunchpadViewModel @Inject constructor(
    private val headlinesRepository: HeadlinesRepository,
) : ViewModel() {

    private val _sources: MutableStateFlow<List<SourceItem>> = MutableStateFlow(mutableListOf())
    val sources: StateFlow<List<SourceItem>> = _sources

    val pagingState: StateFlow<PagingData<TopHeadlineItem>> = MutableStateFlow(PagingData.empty())

    init {
        getTopHeadlines()
        if (FlavorChecker.isFullFlavor()) {
            getSources()
        }
    }

    private fun getSources() {
        headlinesRepository.getSources().onEach { result ->
            when (result) {
                is Resource.Error, Resource.Loading -> _sources.value = emptyList()
                is Resource.Success -> _sources.value = result.data
            }
        }.launchIn(viewModelScope)
    }

    fun getTopHeadlines(source: String = "bbc-news") {
        viewModelScope.launch {
            headlinesRepository.getTopHeadlines(source).distinctUntilChanged()
                .cachedIn(viewModelScope).collect { result ->
                    pagingState.asMutable().emit(result)
                }
        }

    }
}