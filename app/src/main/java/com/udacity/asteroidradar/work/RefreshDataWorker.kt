package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.getDatabase
import retrofit2.HttpException
import java.time.LocalDate

private const val KEY = "xD3AQoZhGup4EAHwQSsog3qkjBMe5Q1ynylSXJ1S"

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
        CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
    /**
     * A coroutine-friendly method to do your work.
     */
    override suspend fun doWork(): Result {
        val startDate = LocalDate.now().toString()
        val endDate = LocalDate.now().plusDays(7).toString()
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepository(database)
        return try {
            repository.refreshAsteroids(startDate, endDate, KEY)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}