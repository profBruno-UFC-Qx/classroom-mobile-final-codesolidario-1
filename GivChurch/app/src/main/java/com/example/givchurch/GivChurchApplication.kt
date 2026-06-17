package com.example.givchurch

import android.app.Application
import com.example.givchurch.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GivChurchApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GivChurchApplication)
            modules(appModules)
        }
    }
}
