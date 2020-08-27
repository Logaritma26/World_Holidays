package com.log.worldholidays.adjust

import com.google.gson.annotations.SerializedName


class CountryNames {

    @SerializedName("name")
    private val name: String? = null

    fun get_name(): String? {
        return name
    }

}