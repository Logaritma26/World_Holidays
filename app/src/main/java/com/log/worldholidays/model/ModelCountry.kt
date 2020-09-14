package com.log.worldholidays.model

import com.google.gson.annotations.SerializedName


data class ModelCountry(

    @SerializedName("name")
    private val name: String?,

    @SerializedName("flag")
    private val flag: String?,

    @SerializedName("capital")
    private val capital: String?,

    @SerializedName("population")
    private val population: String?,

    @SerializedName("area")
    private val area: String?,

    @SerializedName("nativeName")
    private val nativeName: String?,

    @SerializedName("region")
    private val region: String?,
) {

    fun get_name(): String? {
        return name
    }

    fun get_flag(): String? {
        return flag
    }

    fun get_capital(): String? {
        return capital
    }

    fun get_population(): String? {
        return population
    }

    fun get_area(): String? {
        return area
    }

    fun get_nativeName(): String? {
        return nativeName
    }

    fun get_region(): String? {
        return region
    }

}


