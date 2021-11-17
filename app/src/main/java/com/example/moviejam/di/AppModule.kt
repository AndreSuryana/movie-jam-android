package com.example.moviejam.di

import com.example.moviejam.BuildConfig
import com.example.moviejam.constant.Constants
import com.example.moviejam.data.source.remote.MovieJamAPI
import com.example.moviejam.dispatchers.DispatcherProvider
import com.example.moviejam.repository.MainRepository
import com.example.moviejam.repository.MovieJamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieJamAPI(): MovieJamAPI {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(MovieJamAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieJamRepository(api: MovieJamAPI): MainRepository = MovieJamRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}