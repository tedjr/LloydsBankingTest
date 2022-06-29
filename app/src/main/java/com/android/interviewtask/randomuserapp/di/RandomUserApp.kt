package com.android.interviewtask.randomuserapp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RandomUserApp:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RandomUserApp)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}