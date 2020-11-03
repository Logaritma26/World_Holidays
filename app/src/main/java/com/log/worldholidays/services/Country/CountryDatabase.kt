package com.log.worldholidays.services.Country

import android.content.Context
import androidx.room.*
import com.log.worldholidays.model.CountryDB
import com.log.worldholidays.util.Converters

@Database(entities = [CountryDB::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao() : CountryDao

    companion object {

        @Volatile
        private var instance: CountryDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: startInstance(context).also {
                instance = it
            }
        }

        private fun startInstance(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                CountryDatabase::class.java,
                "countrydatabase").build()

    }
}