package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.ImageOfDay
import com.udacity.asteroidradar.api.NasaApiService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import kotlin.collections.ArrayList

enum class ApiStatus { LOADING, ERROR, DONE }
private const val KEY = "xD3AQoZhGup4EAHwQSsog3qkjBMe5Q1ynylSXJ1S"

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    val asteroids = asteroidRepository.asteroids

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _iod = MutableLiveData<ImageOfDay>()
    val iod: LiveData<ImageOfDay>
        get() = _iod

//    private val _asteroids = MutableLiveData<ArrayList<Asteroid>>()
//    val asteroids: LiveData<ArrayList<Asteroid>>
//        get() = _asteroids

    private val _navigateToAsteroid = MutableLiveData<Asteroid?>()
    val navigateToAsteroid: LiveData<Asteroid?>
        get() = _navigateToAsteroid

    init {

        getImageOfDay()
        getAsteroidList()
    }

    private fun getAsteroidList(){
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
        //TODO: When I grabbed the asteroid directly from the api the app worked. I'm struggling figuring out how to wire up the Room database properly
//        val startDate = LocalDate.now().toString()
//        val endDate = LocalDate.now().plusDays(7).toString()
//        viewModelScope.launch {
//            _status.value = ApiStatus.LOADING
//
//            NasaApiService.Companion.AsteroidApi.retrofitService.getAsteroidlist(startDate, endDate, KEY)
//                .enqueue(object : Callback<String> {
//                    override fun onResponse(call: Call<String>, response: Response<String>) {
//                        if (response.body() !== null) {
//                            val result = JSONObject(response.body())
//                            val data = parseAsteroidsJsonResult(result)
//                            _asteroids.value = data
//                        }
//                    }
//
//                    override fun onFailure(call: Call<String>, t: Throwable) {
////                        _status.value = "Failure: " + t.message
//                    }
//                })
//        }
    }


    private fun getImageOfDay() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _iod.value = NasaApiService.Companion.ImageApi.retrofitService.getImageOfDay(KEY)
                _status.value = ApiStatus.DONE

            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _iod.value = null
            }
        }
    }


    fun goToAsteroidDetails(asteroid: Asteroid) {
        _navigateToAsteroid.value = asteroid
    }

}