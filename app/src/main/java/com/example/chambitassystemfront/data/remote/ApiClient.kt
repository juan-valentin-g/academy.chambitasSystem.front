package com.example.chambitassystemfront.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    // Usa tu URL activa (Pinggy o local, asegúrate de que sea la misma para todo)
    private const val BASE_URL = " https://obfem-2806-10ae-10-166e-d73-3bff-3bef-3603.run.pinggy-free.link" // O tu enlace de Pinggy si estás usándolo en físico

    var userToken: String? = null

    // Interceptor para los logs de red (para ver las peticiones en el Logcat de Android Studio)
    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Cliente HTTP unificado con el interceptor de autenticación y logs
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
}