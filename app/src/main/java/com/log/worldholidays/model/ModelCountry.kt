package com.log.worldholidays.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CountryDB(

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val flag: String?,

    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val capital: String?,

    @ColumnInfo(name = "population")
    @SerializedName("population")
    var population: String?,

    @ColumnInfo(name = "area")
    @SerializedName("area")
    var area: String?,

    @ColumnInfo(name = "nativeName")
    @SerializedName("nativeName")
    val nativeName: String?,

    @ColumnInfo(name = "region")
    @SerializedName("region")
    val region: String?,

    @ColumnInfo(name = "alpha2Code")
    @SerializedName("alpha2Code")
    val alpha2Code: String?,

    @ColumnInfo(name = "alpha3Code")
    @SerializedName("alpha3Code")
    val alpha3Code: String?,

    @ColumnInfo(name = "borders")
    @SerializedName("borders")
    val borders: ArrayList<String>?,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false,

    @ColumnInfo(name = "population_int")
    var population_int: Int?,

    @ColumnInfo(name = "area_int")
    var area_int: Int?,

    ) {

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

}





