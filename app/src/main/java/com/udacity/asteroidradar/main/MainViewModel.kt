package com.udacity.asteroidradar.main

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.ImageOfDay
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.ImageApi
import com.udacity.asteroidradar.network.NetworkAsteroid
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import timber.log.Timber

enum class ApiStatus { LOADING, ERROR, DONE }
private const val KEY = "xD3AQoZhGup4EAHwQSsog3qkjBMe5Q1ynylSXJ1S"
private const val START_DATE = "2021-02-25"
private const val END_DATE = "2021-02-26"

class MainViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _iod = MutableLiveData<ImageOfDay>()
    val iod: LiveData<ImageOfDay>
        get() = _iod

    private val _asteroids = MutableLiveData<ArrayList<Asteroid>>()
    val asteroids: LiveData<ArrayList<Asteroid>>
        get() = _asteroids

    init {

        getImageOfDay()
        getAsteroidList()
    }

    private fun getAsteroidList(){
        viewModelScope.launch{
            _status.value = ApiStatus.LOADING
            try {
                val asteroidList: ArrayList<Asteroid> = parseAsteroidsJsonResult(AsteroidApi.retrofitService.getAsteroidlist(START_DATE, END_DATE, KEY))
                _asteroids.value = asteroidList
            }catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _asteroids.value = null
            }
        }
    }


    private fun getImageOfDay() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _iod.value = ImageApi.retrofitService.getImageOfDay(KEY)
                _status.value = ApiStatus.DONE

            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _iod.value = null
            }
        }
    }

    private fun convertArrayList(list1: ArrayList<Asteroid>){

    }


}