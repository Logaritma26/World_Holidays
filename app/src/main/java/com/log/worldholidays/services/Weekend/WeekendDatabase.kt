package com.log.worldholidays.services.Weekend

import android.content.Context
import androidx.room.*
import com.log.worldholidays.model.WeekendDB
import com.log.worldholidays.util.Converters

@Database(entities = [WeekendDB::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class WeekendDatabase : RoomDatabase() {

    abstract fun weekendDao(): WeekendDao

    companion object{

        @Volatile
        private var instance: WeekendDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {

            instance ?: startInstance(context).also {
                instance = it
            }
        }

        private fun startInstance(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                WeekendDatabase::class.java,
                "weekenddatabase").build()

    }

}