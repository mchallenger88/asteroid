package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AsteroidDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DataAsteroid)

    @Insert
    fun insertAsteroid(asteroid: DataAsteroid)

    @Insert
    fun insertIOD(iod: DataImageOfDay)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param asteroid and Image of Day new value to write
     */
    @Update
    fun updateAsteroid(asteroid: DataAsteroid)

    @Update
    fun updateIOD(iod: DataImageOfDay)

    /**
     * Selects and returns the row that matches the key.
     *
     * @param key
     */
    @Query("SELECT * from asteroids_table WHERE Id = :key")
    fun getAsteroid(key: Long): DataAsteroid?

    @Query("SELECT * from image_of_day_table WHERE Id = :key")
    fun getIOD(key: Long): DataImageOfDay?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM asteroids_table")
    fun clearAsteroids()

    @Query("DELETE FROM image_of_day_table")
    fun clearIOD()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM asteroids_table ORDER BY close_approach_date ASC")
    fun getAllAsteroids(): LiveData<List<DataAsteroid>>

    /**
     * Selects and returns the latest image of the day.
     */
    @Query("SELECT * FROM image_of_day_table ORDER BY Id DESC LIMIT 1")
    fun getImageOfDay(): DataImageOfDay?

    /**
     * Selects and returns the night with given nightId.
     */
    @Query("SELECT * from asteroids_table WHERE Id = :key")
    fun getAsteroidWithId(key: Long): LiveData<DataAsteroid>

    @Query("SELECT COUNT(*) FROM asteroids_table")
    fun getCount(): Int
}

