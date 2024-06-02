package com.news.launchpad

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.domain.headlines.HeadlinesRepository
import com.news.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LaunchpadViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val headlinesRepository: HeadlinesRepository,
) : ViewModel() {

    val launchpadUiState = savedStateHandle.getStateFlow<LaunchpadResultUiState>(
        key = KEY_STATE,
        initialValue = LaunchpadResultUiState.Loading
    )


    init {
        getTopHeadlines()
    }

    private fun getTopHeadlines() {
        headlinesRepository.getTopHeadlines().onEach { result ->
            when (result) {
                is Resource.Error -> setState(LaunchpadResultUiState.Error)
                Resource.Loading -> setState(LaunchpadResultUiState.Loading)
                is Resource.Success -> if (result.data.isNotEmpty()) setState(
                    LaunchpadResultUiState.Success(
                        result.data
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    fun sorterDate(sorterDate: SorterDate) {
        viewModelScope.launch {
            if (getState() is LaunchpadResultUiState.Success) {
                val result = getState() as LaunchpadResultUiState.Success
                val sortedList = if (sorterDate == SorterDate.ASC) {
                    result.topHeadlineItems.sortedBy { it.publishedAt }
                } else {
                    result.topHeadlineItems.sortedByDescending {
                        it.publishedAt
                    }
                }
                setState(LaunchpadResultUiState.Success(sortedList))
            }
        }
    }

    private fun setState(launchpadResultUiState: LaunchpadResultUiState) {
        savedStateHandle[KEY_STATE] = launchpadResultUiState
    }

    private fun getState(): LaunchpadResultUiState =
        savedStateHandle[KEY_STATE] ?: LaunchpadResultUiState.Loading


    companion object {
        private const val KEY_STATE = "state"
    }

}