package com.coding.org.domain.di

import com.coding.org.domain.GetMoviesUseCase
import com.coding.org.domain.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    fun providesGetUseCase(repository: MovieRepository) : GetMoviesUseCase {
        return GetMoviesUseCase(repository)
    }
}