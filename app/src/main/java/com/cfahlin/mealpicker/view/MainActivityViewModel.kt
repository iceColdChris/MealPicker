package com.cfahlin.mealpicker.view

import com.cfahlin.mealpicker.BuildConfig
import com.cfahlin.mealpicker.api.model.Results
import com.cfahlin.mealpicker.repository.Repository
import com.cfahlin.mealpicker.util.SchedulerProvider
import io.reactivex.Single

class MainActivityViewModel(private val repository: Repository, private val schedulerProvider: SchedulerProvider) {

    fun showDataFromApi(location: String, radius: String): Single<Results> = repository.getDataFromApi(location,radius,"meal_takeaway",BuildConfig.GoogleSecAPIKEY)
            .compose(schedulerProvider.getSchedulersForSingle())
}