package com.news.util

sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class Error<out T>(val message: String) : Resource<T>()
    data object Loading : Resource<Nothing>()
}