package com.log.worldholidays.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class WeekendDB(

    @ColumnInfo(name = "startDate")
    @SerializedName("startDate")
    val startDate: String?,

    @ColumnInfo(name = "endDate")
    @SerializedName("endDate")
    val endDate: String?,

    @ColumnInfo(name = "dayCount")
    @SerializedName("dayCount")
    val dayCount: Int?,

    @ColumnInfo(name = "needBridgeDay")
    @SerializedName("needBridgeDay")
    val needBridgeDay: Boolean?,

    @ColumnInfo(name = "alpha2Code")
    var alpha2Code: String?,

    ) {

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

}