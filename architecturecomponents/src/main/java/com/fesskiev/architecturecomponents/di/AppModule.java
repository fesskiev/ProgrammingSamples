package com.fesskiev.architecturecomponents.di;


import android.content.Context;

import com.fesskiev.architecturecomponents.utils.Utils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideApplication() {
        return context;
    }

    @Provides
    @Singleton
    Utils provideUtils(Context context) {
        return new Utils(context);
    }
}