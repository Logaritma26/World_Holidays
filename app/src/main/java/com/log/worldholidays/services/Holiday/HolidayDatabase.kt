package com.log.worldholidays.services.Holiday

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.log.worldholidays.model.HolidayDB
import com.log.worldholidays.util.Converters

@Database(entities = [HolidayDB::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class HolidayDatabase : RoomDatabase() {

    abstract fun holidayDao(): HolidayDao

    companion object {

        @Volatile
        private var instance: HolidayDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: startInstance(context).also {
                instance = it
            }
        }

        private fun startInstance(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                HolidayDatabase::class.java,
                "holidaydatabase").build()

    }

}
