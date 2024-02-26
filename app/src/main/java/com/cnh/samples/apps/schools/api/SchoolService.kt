package com.cnh.samples.apps.schools.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Used to connect to the cityofnewyork API to fetch schools
 */
interface SchoolService {

    /** Igor - TODO with more time: add pagination */
    @GET("resource/s3k6-pzi2.json")
    suspend fun getSchools(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    )

    companion object {
        private const val BASE_URL = "https://data.cityofnewyork.us/"

        fun create(): SchoolService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client =
                OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SchoolService::class.java)
        }
    }
}
