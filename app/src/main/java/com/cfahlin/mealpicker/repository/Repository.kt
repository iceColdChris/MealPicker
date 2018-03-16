package com.cfahlin.mealpicker.repository

import com.cfahlin.mealpicker.api.model.IpAddress
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton
import com.cfahlin.mealpicker.api.ApiService

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {

    fun getDataFromApi(): Single<IpAddress> = apiService.getJsonResponse()

}