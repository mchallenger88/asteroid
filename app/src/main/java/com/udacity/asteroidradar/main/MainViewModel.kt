package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.*
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.ImageOfDay
import com.udacity.asteroidradar.api.NasaApiService
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate


enum class ApiStatus { LOADING, ERROR, DONE }
private const val KEY = "xD3AQoZhGup4EAHwQSsog3qkjBMe5Q1ynylSXJ1S"

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    private val _eventNetworkError = MutableLiveData<Boolean>()
    val eventNetworkError: LiveData<Boolean>
            get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>()
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _iod = MutableLiveData<ImageOfDay>()
    val iod: LiveData<ImageOfDay>
        get() = _iod

    var asteroids: LiveData<List<Asteroid>>

    private val _size = MutableLiveData<Int>()
    val size: LiveData<Int>
            get() = _size

    private val _navigateToAsteroid = MutableLiveData<Asteroid?>()
    val navigateToAsteroid: LiveData<Asteroid?>
        get() = _navigateToAsteroid

    init {

        refreshDataFromRepository()
        asteroids = asteroidRepository.asteroids
        getImageOfDay()

    }

    private fun refreshDataFromRepository() {
        val startDate = LocalDate.now().toString()
        val endDate = LocalDate.now().plusDays(7).toString()
        viewModelScope.launch {
            try {
                asteroidRepository.refreshAsteroids(startDate, endDate, KEY)

                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {

            }
        }
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

