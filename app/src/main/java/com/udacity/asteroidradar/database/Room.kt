
package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.*


@Database(entities = [DataAsteroid::class, DataImageOfDay::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract val asteroidDatabaseDao: AsteroidDatabaseDao
}

private lateinit var INSTANCE: AsteroidsDatabase

fun getDatabase(context: Context): AsteroidsDatabase {
    synchronized(AsteroidsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AsteroidsDatabase::class.java,
                "asteroids_database").build()
        }
    }
    return INSTANCE
}