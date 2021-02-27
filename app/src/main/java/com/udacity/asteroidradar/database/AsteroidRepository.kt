package com.udacity.asteroidradar.database

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.NasaApiService
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.domain.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.time.LocalDate

private const val KEY = "xD3AQoZhGup4EAHwQSsog3qkjBMe5Q1ynylSXJ1S"

class AsteroidRepository(private val database: AppDatabase) {

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDatabaseDao.getAllAsteroids()) {
        it.asDomainModel()
    }

    suspend fun refreshAsteroids() {
        val startDate = LocalDate.now().toString()
        val endDate = LocalDate.now().plusDays(7).toString()
        withContext(Dispatchers.IO) {
            NasaApiService.Companion.AsteroidApi.retrofitService.getAsteroidlist(startDate, endDate, KEY)
                    .enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.body() !== null) {
                            val result = JSONObject(response.body())
                            val data = parseAsteroidsJsonResult(result)
                            database.asteroidDatabaseDao.insertAll(*data.asDatabaseModel(data))
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                           Timber.e(t)
                    }
            })
//
        }
    }

}

