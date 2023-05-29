package com.xdlamni.weatherforecast.di

import com.xdlamni.weatherforecast.BuildConfig
import com.xdlamni.weatherforecast.api.service.ForecastApi
import com.xdlamni.weatherforecast.api.repository.ForecastRepository
import com.xdlamni.weatherforecast.helpers.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkApiModule {

    @Provides
    @Singleton
    fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        val requestInterceptor = Interceptor {chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("appid", BuildConfig.API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }

        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .readTimeout(Constants.MAX_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(Constants.MAX_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun createRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideForecastApi(retrofit: Retrofit): ForecastApi {
        return retrofit.create(ForecastApi::class.java)
    }
}