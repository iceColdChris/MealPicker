package com.cfahlin.mealpicker.base

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class
))
interface ApplicationComponent {
    fun inject(mealPickerApplication: MealPickerApplication)
}