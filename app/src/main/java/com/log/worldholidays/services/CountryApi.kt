package com.log.worldholidays.services

import com.log.worldholidays.model.ModelCountry
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface CountryApi {

    @GET
    fun getCountries(@Url url : String): Single<List<ModelCountry>>

}