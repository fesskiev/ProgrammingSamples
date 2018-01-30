package com.fesskiev.kotlinsamples.di

import android.content.Context
import com.fesskiev.kotlinsamples.BuildConfig
import com.fesskiev.kotlinsamples.network.MockingInterceptor
import com.fesskiev.kotlinsamples.network.RequestsHandler
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRequestsHandler(context: Context): RequestsHandler {
        return RequestsHandler(context)
    }

    @Provides
    @Singleton
    fun provideSecurityRandom(): SecureRandom {
        return SecureRandom()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideInterceptor(requestsHandler: RequestsHandler, secureRandom: SecureRandom): Interceptor {
        return MockingInterceptor(requestsHandler, secureRandom)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        return builder.addInterceptor(interceptor)
                .build()
    }
}