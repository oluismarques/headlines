package com.news.launchpad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.domain.headlines.HeadlinesRepository
import com.news.util.Resource
import com.news.util.asMutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class LaunchpadViewModel @Inject constructor(
    private val headlinesRepository: HeadlinesRepository,
) : ViewModel() {

    val launchpadUiState: StateFlow<LaunchpadResultUiState> =
        MutableStateFlow<LaunchpadResultUiState>(LaunchpadResultUiState.Loading)

    init {
        getTopHeadlines()
    }

    private fun getTopHeadlines() {
        headlinesRepository.getTopHeadlines() .onEach { result ->
            when (result) {
                is Resource.Error -> launchpadUiState.asMutable().emit(LaunchpadResultUiState.Error)
                Resource.Loading -> launchpadUiState.asMutable().emit(LaunchpadResultUiState.Loading)
                is Resource.Success -> if (result.data.isNotEmpty()) launchpadUiState.asMutable()
                    .emit(LaunchpadResultUiState.Success(result.data))
            }
        }.launchIn(viewModelScope)
    }

}