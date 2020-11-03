package com.log.worldholidays.services.Holiday

import com.log.worldholidays.model.HolidayDB
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface HolidayApi {

    @GET("PublicHolidays/{year}/{code}")
    fun getHolidays(@Path("code") code: String, @Path("year") year: Int): Single<List<HolidayDB>>

}