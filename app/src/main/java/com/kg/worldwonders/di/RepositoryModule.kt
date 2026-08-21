package com.kg.worldwonders.di

import com.kg.worldwonders.data.repository.WebcamRepositoryImpl
import com.kg.worldwonders.domain.repository.WebcamRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindWebcamRepository(
        repositoryImpl: WebcamRepositoryImpl
    ) : WebcamRepository
}