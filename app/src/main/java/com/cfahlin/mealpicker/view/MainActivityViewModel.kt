package com.cfahlin.mealpicker.view

import com.cfahlin.mealpicker.api.model.IpAddress
import com.cfahlin.mealpicker.repository.Repository
import com.cfahlin.mealpicker.util.SchedulerProvider
import io.reactivex.Single

class MainActivityViewModel(private val repository: Repository, private val schedulerProvider: SchedulerProvider) {

    fun showDataFromApi(): Single<IpAddress> = repository.getDataFromApi()
            .compose(schedulerProvider.getSchedulersForSingle())
}