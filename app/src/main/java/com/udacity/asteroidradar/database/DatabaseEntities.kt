package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroids_table")
data class DataAsteroid constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val code_name: String,
    val close_approach_date: Double,
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
//fun List<NearEarthObjects>.asDomainModel(): List<MediaStore.Video> {
//    return map {
//        MediaStore.Video(
//            url = it.url,
//            title = it.title,
//            description = it.description,
//            updated = it.updated,
//            thumbnail = it.thumbnail
//        )
//    }
//}

