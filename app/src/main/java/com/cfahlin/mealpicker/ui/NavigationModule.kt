package com.cfahlin.mealpicker.ui

import com.cfahlin.mealpicker.di.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class NavigationModule {
    @Binds
    @ActivityScope
    abstract fun provideScreenNavigator(screenNavigator: ScreenNavigator): ScreenNavigator
}