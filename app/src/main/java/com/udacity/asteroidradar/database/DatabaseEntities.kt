package com.udacity.asteroidradar.database

import android.provider.MediaStore
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Asteroid

@Entity(tableName = "asteroids_table")
data class DataAsteroid constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val code_name: String,
    val close_approach_date: String,
    val absolute_magnitude: Double,
    val estimated_diameter_max: Double,
    val is_potentially_hazardous: Boolean,
    val relative_velocity: Double,
    val distance_from_earth: Double
    )

@Entity(tableName = "image_of_day_table")
data class DataImageOfDay constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val url: String,
    val title: String
)

fun List<DataAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.code_name,
            closeApproachDate = it.close_approach_date,
            absoluteMagnitude = it.absolute_magnitude,
            estimatedDiameter = it.estimated_diameter_max,
            relativeVelocity = it.relative_velocity,
            distanceFromEarth = it.distance_from_earth,
            isPotentiallyHazardous = it.is_potentially_hazardous
        )
    }
}

