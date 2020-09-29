package com.log.worldholidays.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.log.worldholidays.model.CountryDB

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg countries: CountryDB) : List<Long>

    @Query("SELECT * FROM CountryDB")
    suspend fun getAllCountries(): List<CountryDB>

    @Query("DELETE FROM CountryDB")
    suspend fun deleteAllCountries()



    @Query("UPDATE CountryDB SET favorite = :boolean WHERE uuid = :countryId")
    suspend fun updateFav(countryId : Int, boolean: Boolean)

    @Query("SELECT * FROM CountryDB WHERE favorite = :fav")
    suspend fun getFavs(fav: Boolean = true): List<CountryDB>

/*

    @Query("SELECT * FROM CountryDB WHERE region = :region")
    suspend fun getAfrica(region: String = "Africa"): List<CountryDB>

    @Query("SELECT * FROM CountryDB WHERE region = :region")
    suspend fun getAsia(region: String = "Asia"): List<CountryDB>

    @Query("SELECT * FROM CountryDB WHERE region = :region")
    suspend fun getAmericas(region: String = "Americas"): List<CountryDB>

    @Query("SELECT * FROM CountryDB WHERE region = :region")
    suspend fun getEurope(region: String = "Europe"): List<CountryDB>

    @Query("SELECT * FROM CountryDB WHERE region = :region")
    suspend fun getOceania(region: String = "Oceania"): List<CountryDB>*/

    @Query("SELECT * FROM CountryDB WHERE region = :region")
    suspend fun getByRegions(region: String): List<CountryDB>




    @Query("SELECT * FROM CountryDB WHERE name LIKE :query")
    suspend fun getFamilar(query: String): List<CountryDB>

    @Query("SELECT * FROM CountryDB WHERE name = :query")
    suspend fun getSingle(query: String): CountryDB



}