package com.log.worldholidays.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Date

@Entity
data class HolidayDB(

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "localName")
    @SerializedName("localName")
    val localName: String?,

    @ColumnInfo(name = "date")
    @SerializedName("date")
    val date: String?,

    /*@ColumnInfo(name = "date2")
    val date2: Date?,*/

    @ColumnInfo(name = "countryCode")
    @SerializedName("countryCode")
    val countryCode: String?,

){

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

}

