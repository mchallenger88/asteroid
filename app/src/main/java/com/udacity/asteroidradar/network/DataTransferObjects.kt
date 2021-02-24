package com.udacity.asteroidradar.network

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.Asteroid

@JsonClass(generateAdapter = true)
data class AsteroidContainer(val asteroids: List<Asteroid>)