package com.udacity.asteroidradar.network

import androidx.lifecycle.MutableLiveData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.domain.ImageOfDay
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val IMAGE_URL = "https://api.nasa.gov/planetary/"
private const val ASTEROID_URL = "https://api.nasa.gov/neo/rest/v1/"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * A retrofit service to fetch a data from NASA.
 */
interface AsteroidApiService {
    @GET("feed")
    suspend fun getAsteroidlist(
            @Query("start_date") start_date: String?,
            @Query("end_date") end_date: String?,
            @Query("api_key") key: String?
    ): JSONObject
}

//Deferred<AsteroidContainer>
private val retrofit = Retrofit.Builder()
        .baseUrl(ASTEROID_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


object AsteroidApi {
    // Configure retrofit to parse JSON and use coroutines
    val retrofitService : AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}



interface ImageApiService {
    @GET("apod")
    suspend fun getImageOfDay(@Query("api_key") key: String?): ImageOfDay
}

private val imageRetrofit = Retrofit.Builder()
        .baseUrl(IMAGE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

object ImageApi {
    val retrofitService : ImageApiService by lazy {
        imageRetrofit.create(ImageApiService::class.java)
    }

}
