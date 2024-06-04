package com.news.data.network.di

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.util.DebugLogger
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.news.core.data.BuildConfig
import com.news.data.network.interceptor.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    internal fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        okhttpCallFactory: Call.Factory,
        networkJson: Json,
    ): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(BuildConfig.BaseUrl)
            .callFactory(okhttpCallFactory)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    internal fun okHttpCallFactory(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor,
    ): Call.Factory =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor).build()


    @Provides
    @Singleton
    internal fun imageLoader(
        okHttpClient: OkHttpClient,
        @ApplicationContext application: Context,
    ): ImageLoader = ImageLoader.Builder(application)
        .okHttpClient(okHttpClient)
        .components {
            add(SvgDecoder.Factory())
        }
        // Assume most content images are versioned urls
        // but some problematic images are fetching each time
        .respectCacheHeaders(false)
        .apply {
            if (BuildConfig.DEBUG) {
                logger(DebugLogger())
            }
        }
        .build()
}


