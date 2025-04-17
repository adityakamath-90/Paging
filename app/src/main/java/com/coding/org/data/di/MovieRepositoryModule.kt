package com.coding.org.data.di

import com.coding.org.data.MovieRepositoryImpl
import com.coding.org.data.local.TmdbDatabase
import com.coding.org.data.remote.TmdbService
import com.coding.org.domain.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MovieRepositoryModule {
    @Provides
    fun movieRepository(service: TmdbService, database: TmdbDatabase) : MovieRepository {
        return MovieRepositoryImpl(service, database)
    }
}