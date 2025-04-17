package com.coding.org.data.di

import com.coding.org.data.remote.TmdbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient
            .Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    fun providesLogging() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Provides
    fun providesMoviesApi(retrofit: Retrofit) : TmdbService {
        return retrofit.create(TmdbService::class.java)
    }
}