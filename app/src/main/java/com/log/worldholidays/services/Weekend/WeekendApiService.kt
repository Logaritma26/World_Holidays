package com.log.worldholidays.services.Weekend

import com.log.worldholidays.model.WeekendDB
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeekendApiService {

    private val BASE_URL = "https://date.nager.at/Api/v2/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(WeekendApi::class.java)

    fun getWeekends(code: String, year: Int): Single<List<WeekendDB>> {
        return api.getWeekends(year, code)
    }

}