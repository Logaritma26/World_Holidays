package com.log.worldholidays.services.Weekend

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.log.worldholidays.model.HolidayDB
import com.log.worldholidays.model.WeekendDB

@Dao
interface WeekendDao {

    @Insert
    suspend fun insertAll(vararg countries: WeekendDB) : List<Long>

    @Query("SELECT * FROM WeekendDB")
    suspend fun getAllWeekends(): List<WeekendDB>




    @Query("DELETE FROM WeekendDB")
    suspend fun deleteAllWeekends()

    @Query("DELETE FROM WeekendDB WHERE alpha2Code = :code2")
    suspend fun deleteWeekendsOfCountry(code2: String)



    @Query("SELECT * FROM WeekendDB WHERE alpha2Code = :code2")
    suspend fun getWeekendsWithCode(code2: String): List<WeekendDB>

    @Query("SELECT DISTINCT alpha2Code FROM WeekendDB")
    suspend fun distinct_countries(): List<String>


}