package com.log.worldholidays.services

import com.log.worldholidays.model.CountryDB
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET


interface CountryApi {

    @GET("all?fields=name;capital;region;nativeName;population;area;flag;alpha2Code;alpha3Code;borders")
    fun getCountries(): Single<List<CountryDB>>

}