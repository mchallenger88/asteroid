
package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.*


@Database(entities = [DataAsteroid::class, DataImageOfDay::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val asteroidDatabaseDao: AsteroidDatabaseDao
}

private lateinit var INSTANCE: AppDatabase

fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,
                "app_database").build()
        }
    }
    return INSTANCE
}