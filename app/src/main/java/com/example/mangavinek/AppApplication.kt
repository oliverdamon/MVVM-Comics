package com.example.mangavinek

import android.app.Application
import com.example.mangavinek.core.di.module.addModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            Timber.plant(Timber.DebugTree());
            androidLogger()
            androidContext(this@AppApplication)
            modules(addModule)
        }
    }

}