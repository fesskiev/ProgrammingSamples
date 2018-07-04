package com.fesskiev.graphql.di

import android.content.Context
import com.fesskiev.graphql.domain.source.DataRepository
import com.fesskiev.graphql.domain.source.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(context: Context, retrofit: Retrofit): RemoteDataSource {
        return RemoteDataSource(context, retrofit)
    }

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource): DataRepository {
        return DataRepository(remoteDataSource)
    }
}