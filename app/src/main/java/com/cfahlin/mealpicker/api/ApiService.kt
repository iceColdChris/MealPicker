package com.cfahlin.mealpicker.api

import com.cfahlin.mealpicker.api.model.IpAddress
import io.reactivex.Single
import retrofit2.http.GET


interface ApiService {

    @GET(".")
    fun getJsonResponse(): Single<IpAddress>
}