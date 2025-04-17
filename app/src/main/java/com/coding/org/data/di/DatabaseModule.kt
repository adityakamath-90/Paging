package com.coding.org.data.di

import android.content.Context
import androidx.room.Room
import com.coding.org.data.local.TmdbDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : TmdbDatabase {
        return Room.databaseBuilder(
            context,
            TmdbDatabase::class.java,
            "tmdb.db"
        ).build()
    }
}