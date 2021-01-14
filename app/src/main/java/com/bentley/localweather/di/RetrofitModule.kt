package com.bentley.localweather.di

import android.content.Context
import com.bentley.localweather.App
import com.bentley.localweather.data.api.ApiService
import com.bentley.localweather.data.util.AuthInterceptor
import com.bentley.localweather.utils.NetworkCheck
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor, context: Context): OkHttpClient {

        val logger = HttpLoggingInterceptor()
        val cacheSize = (10 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        val httpClient: OkHttpClient.Builder =
            OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)

        with(httpClient) {
            logger.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            logger.setLevel(HttpLoggingInterceptor.Level.BODY)
            addInterceptor(logger)
            addInterceptor(authInterceptor)
            cache(myCache)
        }

        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService {
        return retrofit
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(networkCheck: NetworkCheck): AuthInterceptor {
        return AuthInterceptor(networkCheck)
    }

    @Singleton
    @Provides
    fun provideNetworkCheck(context: Context): NetworkCheck {
        return NetworkCheck(context)
    }

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return App.globalApplicationContext
    }
}