package com.log.worldholidays.services

import com.log.worldholidays.model.CountryDB
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryApiService {

    private val BASE_URL = "https://restcountries.eu/rest/v2/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(CountryApi::class.java)

    fun getData(): Single<List<CountryDB>> {
        return api.getCountries()
    }



}