package com.mathtech.quickmath

import android.app.Application
import com.mathtech.quickmath.di.dataSourceModule
import com.mathtech.quickmath.di.dispatcherModule
import com.mathtech.quickmath.di.repositoryModule
import com.mathtech.quickmath.di.useCaseModule
import com.mathtech.quickmath.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                viewModelModule,
                dispatcherModule,
                dataSourceModule,
                useCaseModule,
                repositoryModule,
            )
        }
    }
}