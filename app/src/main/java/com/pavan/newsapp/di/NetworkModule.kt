package com.pavan.newsapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pavan.newsapp.Constants.BASEURL
import com.pavan.newsapp.Constants.TIMEOUT_TIME
import com.pavan.newsapp.server.newsApiservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideUrl() = BASEURL

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideTimeOut() = TIMEOUT_TIME

    @Provides
    @Singleton
    fun provideClient(time: Long) = OkHttpClient.Builder()
        .connectTimeout(time,TimeUnit.SECONDS)
        .readTimeout(time,TimeUnit.SECONDS)
        .writeTimeout(time,TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseurl : String, gson : Gson, client:OkHttpClient):newsApiservice =
        Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create(gson))
            .client(client).build().create(newsApiservice::class.java)
}