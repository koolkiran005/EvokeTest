package com.kiran.evoketest.android.di

import android.app.Application
import dagger.Module
import javax.inject.Singleton
import dagger.Provides


@Module
class AppModule(val application:Application) {

    @Provides
    @Singleton
    fun providesApplication() = application

}