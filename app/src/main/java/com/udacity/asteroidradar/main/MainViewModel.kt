package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.ImageOfDay
import com.udacity.asteroidradar.api.NasaApiService
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch
import java.time.LocalDate


enum class ApiStatus { LOADING, ERROR, DONE }
private const val KEY = "xD3AQoZhGup4EAHwQSsog3qkjBMe5Q1ynylSXJ1S"

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)


//    fun getAllAsteroids() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(Resource.success(data = repository.getAsteroids()))
//        } catch (exception: Exception) {
//            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
//        }
//    }

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _iod = MutableLiveData<ImageOfDay>()
    val iod: LiveData<ImageOfDay>
        get() = _iod

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    var asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _size = MutableLiveData<Int>()
    val size: LiveData<Int>
            get() = _size

    private val _navigateToAsteroid = MutableLiveData<Asteroid?>()
    val navigateToAsteroid: LiveData<Asteroid?>
        get() = _navigateToAsteroid

    init {
        val startDate = LocalDate.now().toString()
        val endDate = LocalDate.now().plusDays(7).toString()
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids(startDate, endDate, KEY)
        }
        asteroids = asteroidRepository.asteroids
        getImageOfDay()
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

