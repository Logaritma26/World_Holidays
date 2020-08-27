package com.log.worldholidays.adjust

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface JsonPlaceHolder {

    @GET
    fun get_text(@Url url : String): Call<CountryNames?>?

}