package com.log.worldholidays.services

import com.log.worldholidays.model.ModelCountry
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryApiService {

    private val BASE_URL = "https://restcountries.eu/rest/v2"
    private val URL_EX = "/all?fields=name;capital;region;nativeName;population;area;flag"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CountryApi::class.java)

    fun getData(): Single<List<ModelCountry>> {
        return api.getCountries(URL_EX)
    }



}