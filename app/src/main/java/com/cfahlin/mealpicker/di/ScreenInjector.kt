package com.cfahlin.mealpicker.di

import android.app.Activity
import com.bluelinelabs.conductor.Controller
import com.cfahlin.mealpicker.base.BaseActivity
import com.cfahlin.mealpicker.base.BaseController
import dagger.android.AndroidInjector
import javax.inject.Inject
import javax.inject.Provider


@ActivityScope
class ScreenInjector @Inject constructor(private val screenInjectors : Map<Class<out Controller>,
        Provider<AndroidInjector.Factory<out Controller>>>) {
    private val cache:  MutableMap<String, AndroidInjector<out Controller>> = HashMap()

    companion object {
        fun get(activity: Activity): ScreenInjector {
            if(activity !is BaseActivity)
                throw IllegalArgumentException("Controller must be hosted by BaseActivity")
            return activity.screenInjector
        }
    }

    fun inject(controller: Controller) {
        if(controller !is BaseController)
            throw IllegalArgumentException("Controller must extend BaseController")

        val instanceId: String = controller.instanceId

        if(cache.containsKey(instanceId)){
            @Suppress("UNCHECKED_CAST")
            (cache[instanceId] as AndroidInjector<Controller>).inject(controller)
            return
        }

        @Suppress("UNCHECKED_CAST")
        val injectorFactory =
                screenInjectors[controller.javaClass]!!.get() as AndroidInjector.Factory<Controller>

        val injector: AndroidInjector<Controller> = injectorFactory.create(controller)
        cache[instanceId] = injector
        injector.inject(controller)
    }

    fun clear(controller: Controller) {
        if(controller !is BaseController)
            throw IllegalArgumentException("Controller must extend BaseController")
        cache.remove(controller.instanceId)
    }


}