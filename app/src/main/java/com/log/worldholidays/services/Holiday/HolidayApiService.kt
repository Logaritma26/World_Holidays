package com.log.worldholidays.services.Holiday

import com.log.worldholidays.model.HolidayDB
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HolidayApiService {

    private val BASE_URL = "https://date.nager.at/Api/v2/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(HolidayApi::class.java)

    fun getHolidays(code: String, year: Int): Single<List<HolidayDB>> {
        return api.getHolidays(code, year)
    }
}