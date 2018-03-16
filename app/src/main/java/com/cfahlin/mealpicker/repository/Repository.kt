package com.cfahlin.mealpicker.repository

import com.cfahlin.mealpicker.api.ApiService
import com.cfahlin.mealpicker.api.model.Results
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {

    fun getDataFromApi(location: String,
                       radius: String,
                       types: String,
                       key: String): Single<Results> = apiService.getJsonResponse(location, radius, types, key)

}