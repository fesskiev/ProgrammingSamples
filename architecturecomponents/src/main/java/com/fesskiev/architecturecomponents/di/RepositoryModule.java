package com.fesskiev.architecturecomponents.di;

import com.fesskiev.architecturecomponents.domain.source.DataRepository;
import com.fesskiev.architecturecomponents.domain.source.local.LocalDataSource;
import com.fesskiev.architecturecomponents.domain.source.remote.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public DataRepository provideRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        return new DataRepository(remoteDataSource, localDataSource);
    }
}
