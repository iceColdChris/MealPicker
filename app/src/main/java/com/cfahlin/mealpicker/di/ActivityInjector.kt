package com.cfahlin.mealpicker.di

import android.app.Activity
import android.content.Context
import com.cfahlin.mealpicker.base.BaseActivity
import com.cfahlin.mealpicker.base.MealPickerApplication
import dagger.android.AndroidInjector
import javax.inject.Inject
import javax.inject.Provider


class ActivityInjector @Inject constructor(private val activityInjectors : Map<Class<out Activity>,
        Provider<AndroidInjector.Factory<out Activity>>> ) {

    private val cache:  MutableMap<String, AndroidInjector<out Activity>> = HashMap()

    companion object {
        fun get(context: Context): ActivityInjector {
            return (context.applicationContext as MealPickerApplication).activityInjector
        }
    }

    fun inject(activity: Activity) {
        if(activity !is BaseActivity)
            throw IllegalArgumentException("Activity must extend BaseActivity")

        val instanceId: String = activity.instanceID

        if(cache.containsKey(instanceId)){
            @Suppress("UNCHECKED_CAST")
            (cache[instanceId] as AndroidInjector<Activity>).inject(activity)
            return
        }

        @Suppress("UNCHECKED_CAST")
        val injectorFactory =
                activityInjectors[activity.javaClass]!!.get() as AndroidInjector.Factory<Activity>

        val injector: AndroidInjector<Activity> = injectorFactory.create(activity)
        cache[instanceId] = injector
        injector.inject(activity)
    }

    fun clear(activity: Activity) {
        if(activity !is BaseActivity)
            throw IllegalArgumentException("Activity must extend BaseActivity")
        cache.remove(activity.instanceID)
    }

}