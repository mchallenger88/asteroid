package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.domain.Asteroid

@Dao
interface AsteroidDatabaseDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(vararg asteroids: Asteroid)

//    @Insert
//    fun insertIOD(iod: ImageOfDay)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param asteroid and Image of Day new value to write
     */
//    @Update
//    fun updateNEO(asteroid: Asteroid)

//    @Update
//    fun updateIOD(iod: ImageOfDay)

    /**
     * Selects and returns the row that matches the key.
     *
     * @param key
     */
//    @Query("SELECT * from asteroids_table WHERE Id = :key")
//    fun getNEO(key: Long): Asteroid?

//    @Query("SELECT * from image_of_day_table WHERE Id = :key")
//    fun getIOD(key: Long): ImageOfDay?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
//    @Query("DELETE FROM asteroids_table")
//    fun clearNEO()

//    @Query("DELETE FROM image_of_day_table")
//    fun clearIOD()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
//    @Query("SELECT * FROM asteroids_table ORDER BY Id DESC")
//    fun getAllAsteroids(): LiveData<List<Asteroid>>

    /**
     * Selects and returns the latest image of the day.
     */
//    @Query("SELECT * FROM image_of_day_table ORDER BY Id DESC LIMIT 1")
//    fun getImageOfDay(): ImageOfDay?

    /**
     * Selects and returns the night with given nightId.
     */
//    @Query("SELECT * from asteroids_table WHERE Id = :key")
//    fun getAsteroidWithId(key: Long): LiveData<Asteroid>
}

