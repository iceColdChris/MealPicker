package com.cfahlin.mealpicker.view.di

import com.cfahlin.mealpicker.repository.Repository
import com.cfahlin.mealpicker.util.SchedulerProvider
import com.cfahlin.mealpicker.view.MainActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun provideViewModel(repository: Repository, schedulerProvider: SchedulerProvider) = MainActivityViewModel(repository, schedulerProvider)
}