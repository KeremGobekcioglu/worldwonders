package com.kg.worldwonders.di

import com.kg.worldwonders.BuildConfig
import com.kg.worldwonders.common.constants.ApiConstants
import com.kg.worldwonders.data.WindyApi
import com.kg.worldwonders.data.WindyApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule
{

    @Provides @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true   // null -> default instead of crashing
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient , json: Json) : Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: WindyApiKeyInterceptor) : OkHttpClient
    {
        /*no logging on prod.*/
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideWindyApi(retrofit: Retrofit) : WindyApi
    {
        return retrofit.create(WindyApi::class.java)
    }
}