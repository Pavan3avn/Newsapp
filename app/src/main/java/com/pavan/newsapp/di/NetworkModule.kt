package com.pavan.newsapp.di

import com.google.gson.Gson
import com.pavan.newsapp.Constants
import com.pavan.newsapp.Constants.BASEURL
import com.pavan.newsapp.Constants.TIMEOUT_TIME
import com.pavan.newsapp.server.newsApiservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("BASE_URL")
    fun provideBaseUrl(): String = BASEURL
    @Provides
    @Singleton
    fun provideGson() = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideTimeOut() = TIMEOUT_TIME


    @Provides
    @Singleton
    fun provideHttpInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply{
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    @Singleton
    fun provideClient(time: Long,interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(time,TimeUnit.SECONDS)
        .readTimeout(time,TimeUnit.SECONDS)
        .writeTimeout(time,TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(@Named("BASE_URL")baseurl: String, gson : GsonConverterFactory, client:OkHttpClient):Retrofit=
        Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(gson)
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):newsApiservice{
        return retrofit.create(newsApiservice::class.java)
    }
}