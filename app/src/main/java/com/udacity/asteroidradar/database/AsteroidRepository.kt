package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.domain.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await

class AsteroidRepository(private val database: AppDatabase){

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDatabaseDao.getAllAsteroids()) {
        it.asDomainModel()
    }

    suspend fun clearAsteroids(){
        withContext(Dispatchers.IO) {
            database.asteroidDatabaseDao.clearAsteroids()
        }
    }

    suspend fun refreshAsteroids(startDate: String, endDate: String, KEY: String) {
        withContext(Dispatchers.IO) {
            val result = AsteroidHelper(NasaApiService.Companion.AsteroidApi, startDate, endDate, KEY ).getAsteroids().await()
            val data = JSONObject(result)
            val asteroids = parseAsteroidsJsonResult(data)
            try {
                database.asteroidDatabaseDao.insertAll(*asteroids.asDatabaseModel(asteroids))
            }catch(e: Exception){
                println(e.message)
            }

        }
    }


}

