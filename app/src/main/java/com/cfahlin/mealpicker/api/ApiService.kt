package com.cfahlin.mealpicker.api

import com.cfahlin.mealpicker.api.model.Results
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("json")
    fun getJsonResponse(@Query("location") location: String,
                        @Query("radius") radius: String,
                        @Query("types") types: String,
                        @Query("key") key: String): Single<Results>
}