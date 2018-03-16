package com.cfahlin.mealpicker.di

import com.cfahlin.mealpicker.view.MainActivity
import com.cfahlin.mealpicker.view.di.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity
}