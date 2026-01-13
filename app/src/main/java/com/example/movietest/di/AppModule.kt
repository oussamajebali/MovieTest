package com.example.movietest.di

import com.example.movietest.BuildConfig
import com.example.movietest.data.api.OmdbApi
import com.example.movietest.data.repository.MovieRepositoryImpl
import com.example.movietest.domain.repository.MovieRepository
import com.example.movietest.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(Constants.NETWORK_TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(Constants.NETWORK_TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(Constants.NETWORK_TIMEOUT_WRITE, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideOmdbApi(okHttpClient: OkHttpClient): OmdbApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.OMDB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: OmdbApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }
}