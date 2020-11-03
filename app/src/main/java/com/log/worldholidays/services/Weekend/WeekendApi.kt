package com.log.worldholidays.services.Weekend

import com.log.worldholidays.model.WeekendDB
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WeekendApi {

    @GET("LongWeekend/{year}/{code}")
    fun getWeekends(@Path("year") year: Int, @Path("code") code: String): Single<List<WeekendDB>>

}