package com.kiran.evoketest.android

import android.app.Application
import com.kiran.evoketest.android.di.AppComponent
import com.kiran.evoketest.android.di.AppModule
import com.kiran.evoketest.android.di.DaggerAppComponent
import com.kiran.evoketest.android.di.NetModule


class App : Application() {
    private var mAppComponent: AppComponent? = null
    override fun onCreate() {
        super.onCreate()

        mAppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule("https://jsonplaceholder.typicode.com/"))
            .build()
    }

    fun getAppComponent() = mAppComponent
}