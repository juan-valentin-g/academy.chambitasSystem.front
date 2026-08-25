package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        SessionManager.authorizationHeader?.let { authorization ->
            requestBuilder.header("Authorization", authorization)
        }

        val response = chain.proceed(requestBuilder.build())

        if (response.code == 401 && SessionManager.hasActiveSession) {
            SessionManager.clearSession()
        }

        return response
    }
}
