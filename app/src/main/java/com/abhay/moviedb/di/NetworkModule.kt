package com.abhay.moviedb.di

import com.abhay.moviedb.BuildConfig
import com.abhay.moviedb.data.remote.services.MovieApiService
import com.abhay.moviedb.util.Constants
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        val okHttpClient = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClient.addInterceptor(loggingInterceptor)
        }

        okHttpClient
            .addInterceptor(
                Interceptor { chain ->
                    val url = chain.request().url.newBuilder()
                        .addQueryParameter("api_key", Constants.apiKey)
                        .build()
                    val request = chain.request().newBuilder()
                        .url(url)
                    return@Interceptor chain.proceed(request.build())
                }
            )
            .readTimeout(Constants.API_TIME_OUT_IN_SEC, TimeUnit.SECONDS)
            .connectTimeout(Constants.API_TIME_OUT_IN_SEC, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitMock(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()


    @Provides
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)


}