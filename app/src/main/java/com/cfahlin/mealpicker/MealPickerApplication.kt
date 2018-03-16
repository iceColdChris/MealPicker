package com.cfahlin.mealpicker

import android.app.Activity
import android.app.Application
import com.cfahlin.mealpicker.di.DaggerAppComponent
import com.cfahlin.mealpicker.util.CrashReportingTree
import com.crashlytics.android.Crashlytics
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import javax.inject.Inject


class MealPickerApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

        Fabric.with(this, Crashlytics())

        Timber.plant(if (BuildConfig.DEBUG)
            Timber.DebugTree()
        else
            CrashReportingTree())
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector
}