package com.cfahlin.mealpicker.base

import android.app.Application
import com.cfahlin.mealpicker.BuildConfig
import com.cfahlin.mealpicker.di.ActivityInjector
import timber.log.Timber
import javax.inject.Inject

class MealPickerApplication: Application() {

    @Inject lateinit var activityInjector: ActivityInjector
    var component: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        component = initComponent()
        component!!.inject(this)

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    protected fun initComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

}