package com.log.worldholidays.services.Country

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

    @Query("UPDATE CountryDB SET favorite = :boolean WHERE alpha2Code = :code2")
    suspend fun updateFav_withCode2(code2 : String, boolean: Boolean)

    @Query("SELECT alpha2Code FROM CountryDB WHERE favorite = :fav")
    suspend fun getFavs(fav: Boolean = true): List<String>

    @Query("SELECT * FROM CountryDB WHERE region = :region")
    suspend fun getByRegions(region: String): List<CountryDB>




    @Query("SELECT * FROM CountryDB WHERE name LIKE :query AND region = :region")
    suspend fun getFamilar(query: String, region: String): List<CountryDB>

    @Query("SELECT * FROM CountryDB WHERE name LIKE :query")
    suspend fun getFamilar(query: String): List<CountryDB>





    @Query("SELECT flag FROM CountryDB WHERE alpha3Code = :alphaCode3")
    suspend fun getFlagWithCode3(alphaCode3: String): String

    @Query("SELECT flag FROM CountryDB WHERE alpha2Code = :alphaCode2")
    suspend fun getFlagWithCode2(alphaCode2: String): String



    @Query("SELECT * FROM CountryDB WHERE alpha3Code = :query")
    suspend fun getFromAlpha3(query: String): CountryDB

    @Query("SELECT * FROM CountryDB WHERE alpha2Code = :code2")
    suspend fun getFromAlpha2(code2: String): CountryDB

    @Query("SELECT alpha3Code FROM CountryDB WHERE alpha2Code = :code2")
    suspend fun convert_Code2_to_Code3(code2: String): String

    @Query("SELECT alpha2Code FROM CountryDB WHERE alpha3Code = :code3")
    suspend fun convert_Code3_to_Code2(code3: String): String



    @Query("SELECT * FROM CountryDB WHERE name = :query")
    suspend fun getSingle(query: String): CountryDB



    @Query("SELECT * FROM CountryDB WHERE region = :region ORDER BY population_int ASC ")
    suspend fun ASC_Sorted_Population(region: String): List<CountryDB>

    @Query("SELECT * FROM CountryDB WHERE region = :region ORDER BY population_int DESC ")
    suspend fun DESC_Sorted_Population(region: String): List<CountryDB>

    @Query("SELECT * FROM CountryDB ORDER BY population_int ASC ")
    suspend fun ASC_ALL_Sorted_Population(): List<CountryDB>

    @Query("SELECT * FROM CountryDB ORDER BY population_int DESC ")
    suspend fun DESC_ALL_Sorted_Population(): List<CountryDB>


    @Query("SELECT * FROM CountryDB WHERE region = :region ORDER BY area_int ASC ")
    suspend fun ASC_Sorted_Area(region: String): List<CountryDB>

    @Query("SELECT * FROM CountryDB WHERE region = :region ORDER BY area_int DESC ")
    suspend fun DESC_Sorted_Area(region: String): List<CountryDB>

    @Query("SELECT * FROM CountryDB ORDER BY area_int ASC ")
    suspend fun ASC_ALL_Sorted_Area(): List<CountryDB>

    @Query("SELECT * FROM CountryDB ORDER BY area_int DESC ")
    suspend fun DESC_ALL_Sorted_Area(): List<CountryDB>


}