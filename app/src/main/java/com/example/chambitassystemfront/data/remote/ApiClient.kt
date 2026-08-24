package com.example.chambitassystemfront.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



object ApiClient {
    private const val BASE_URL = "https://thikl-189-129-42-124.run.pinggy-free.link"

    var userToken: String? = null

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()

            // Si hay un token guardado, se añade automáticamente en las cabeceras
            userToken?.let { token ->
                val cleanToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                requestBuilder.header("Authorization", cleanToken)
            }

            chain.proceed(requestBuilder.build())
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    // Instancia única de Retrofit para toda la app
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Servicios de la API centralizados
    val authApiService: AuthApiService by lazy { retrofit.create(AuthApiService::class.java) }
    val jobsApiService: JobsApiService by lazy { retrofit.create(JobsApiService::class.java) }
    val categoryApiService: CategoryApiService by lazy { retrofit.create(CategoryApiService::class.java) }
    val matchesApiService: MatchesApiService by lazy { retrofit.create(MatchesApiService::class.java) }
    val applicationsApiService: ApplicationsApiService by lazy { retrofit.create(ApplicationsApiService::class.java) }
    val usersApiService: UsersApiService by lazy { retrofit.create(UsersApiService::class.java) }
}