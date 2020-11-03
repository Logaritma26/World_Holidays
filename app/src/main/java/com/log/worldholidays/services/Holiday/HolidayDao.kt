package com.log.worldholidays.services.Holiday

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.log.worldholidays.model.HolidayDB

@Dao
interface HolidayDao {

    @Insert
    suspend fun insertAll(vararg countries: HolidayDB) : List<Long>

    @Query("SELECT * FROM HolidayDB")
    suspend fun getAllHolidays(): List<HolidayDB>



    @Query("DELETE FROM HolidayDB")
    suspend fun deleteAllHolidays()

    @Query("DELETE FROM HolidayDB WHERE countryCode = :code")
    suspend fun deleteHolidaysOfCountry(code: String)

    /*@Query("DELETE FROM HolidayDB WHERE date")
    suspend fun deleteAllDeprecatedHolidays()*/



    @Query("SELECT DISTINCT countryCode FROM HolidayDB")
    suspend fun distinct_countries(): List<String>

    @Query("SELECT * FROM HolidayDB WHERE countryCode = :code2")
    suspend fun getHolidaysWithCode(code2: String): List<HolidayDB>




}