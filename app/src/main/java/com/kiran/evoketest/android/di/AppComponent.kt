package com.kiran.evoketest.android.di

import com.kiran.evoketest.android.ItemListViewModel
import com.kiran.evoketest.android.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(vm: ItemListViewModel)
}