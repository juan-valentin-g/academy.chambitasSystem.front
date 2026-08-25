package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val baseUrl: String
        get() = BuildConfig.API_BASE_URL.trim().let { configuredUrl ->
            if (configuredUrl.endsWith('/')) configuredUrl else "$configuredUrl/"
        }

    private val logger = HttpLoggingInterceptor().apply {
        redactHeader("Authorization")
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BASIC
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(logger)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApiService: AuthApiService by lazy { retrofit.create(AuthApiService::class.java) }
    val jobsApiService: JobsApiService by lazy { retrofit.create(JobsApiService::class.java) }
    val categoryApiService: CategoryApiService by lazy { retrofit.create(CategoryApiService::class.java) }
    val matchesApiService: MatchesApiService by lazy { retrofit.create(MatchesApiService::class.java) }
    val applicationsApiService: ApplicationsApiService by lazy { retrofit.create(ApplicationsApiService::class.java) }
    val usersApiService: UsersApiService by lazy { retrofit.create(UsersApiService::class.java) }
    val reviewsApiService: ReviewsApiService by lazy { retrofit.create(ReviewsApiService::class.java) }
}
