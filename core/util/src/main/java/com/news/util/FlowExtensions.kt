package com.news.util

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T : Any> fetchMovies(
    ioDispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Flow<Resource<T>> = flow {
    emit(Resource.Loading)
    try {
        val items = apiCall()
        emit(Resource.Success(items))
    } catch (t: Throwable) {
        Log.w("ERROR: ","${t.message}")
        emit(Resource.Error("Failed to load items: ${t.message}"))
    }
}.flowOn(ioDispatcher)

fun <T> StateFlow<T>.asMutable() = this as MutableStateFlow<T>

fun <T> SharedFlow<T>.asMutable() = this as MutableSharedFlow<T>
