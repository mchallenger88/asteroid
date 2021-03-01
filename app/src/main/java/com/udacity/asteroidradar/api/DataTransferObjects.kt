package com.udacity.asteroidradar.api

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.DataAsteroid
import com.udacity.asteroidradar.domain.Asteroid

@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroids: List<NetworkAsteroid>)

@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
        val id: Long,
        val codename: String,
        val closeApproachDate: String,
        val absoluteMagnitude: Double,
        val estimatedDiameter: Double,
        val relativeVelocity: Double,
        val distanceFromEarth: Double,
        val isPotentiallyHazardous: Boolean)



fun ArrayList<Asteroid>.asDatabaseModel(asteroids: ArrayList<Asteroid>): Array<DataAsteroid>{
        return asteroids.map {
                DataAsteroid(
                        id = it.id,
                        code_name = it.codename,
                        close_approach_date = it.closeApproachDate,
                        absolute_magnitude = it.absoluteMagnitude,
                        estimated_diameter_max = it.estimatedDiameter,
                        relative_velocity = it.relativeVelocity,
                        distance_from_earth = it.distanceFromEarth,
                        is_potentially_hazardous = it.isPotentiallyHazardous)
        }.toTypedArray()

}