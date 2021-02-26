package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.domain.ImageOfDay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



/**
 * A retrofit service to fetch a data from NASA.
 */
interface NasaApiService {
    @GET("neo/rest/v1/feed")
    fun getAsteroidlist(
            @Query("start_date") start_date: String?,
            @Query("end_date") end_date: String?,
            @Query("api_key") key: String?
    ): Call<String>

    @GET("planetary/apod")
    suspend fun getImageOfDay(@Query("api_key") key: String?): ImageOfDay


    companion object{
        private const val BASE_URL = "https://api.nasa.gov/"

        private val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        private val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

        private val imageRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

        object AsteroidApi {
            // Configure retrofit to parse JSON and use coroutines
            val retrofitService : NasaApiService by lazy {
                retrofit.create(NasaApiService::class.java)
            }
        }

        object ImageApi {
            val retrofitService : NasaApiService by lazy {
                imageRetrofit.create(NasaApiService::class.java)
            }
        }

    }

}
