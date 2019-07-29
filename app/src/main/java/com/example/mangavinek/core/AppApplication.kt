package com.example.mangavinek.core

import android.app.Application
import com.example.mangavinek.core.di.module.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(myModule)
        }
    }
}