package com.cfahlin.mealpicker.base

import dagger.Module
import android.app.Application
import android.content.Context
import dagger.Provides


@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun provideApplicationContext(): Context {
        return application
    }
}