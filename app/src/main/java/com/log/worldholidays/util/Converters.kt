package com.log.worldholidays.util

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.sql.Date
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


object Converters {

    @TypeConverter
    fun fromString(value: String?): ArrayList<String?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

/*
    @TypeConverter
    fun fromStringToDate(value: String?): Date? {

        val format = SimpleDateFormat("yyyy-MM-dd")
        value?.let {
            val parsed: java.util.Date = format.parse(value)
            val sql = Date(parsed.getTime())
        }


        val listType: Type = object : TypeToken<Date?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromDateToString(value: String?): ArrayList<String?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }*/

}