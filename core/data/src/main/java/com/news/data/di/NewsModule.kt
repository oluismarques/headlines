package com.news.data.di


import com.news.data.repository.HeadlinesRepositoryImp
import com.news.domain.headlines.HeadlinesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NewsModule {

    @Binds
    @Singleton
    fun bindsHeadlinesRepository(
        impl: HeadlinesRepositoryImp,
    ): HeadlinesRepository

}
