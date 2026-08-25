package com.example.chambitassystemfront.data.session

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREFERENCES_NAME = "ChambitasPrefs"
    private const val ACCESS_TOKEN_KEY = "USER_TOKEN"
    private const val USER_ID_KEY = "USER_ID"
    private const val USER_ROLE_KEY = "USER_ROLE"

    @Volatile
    private var preferences: SharedPreferences? = null

    fun initialize(context: Context) {
        if (preferences == null) {
            synchronized(this) {
                if (preferences == null) {
                    preferences = context.applicationContext.getSharedPreferences(
                        PREFERENCES_NAME,
                        Context.MODE_PRIVATE
                    )
                }
            }
        }
    }

    val accessToken: String?
        get() = preferencesOrThrow().getString(ACCESS_TOKEN_KEY, null)
            ?.removePrefix("Bearer ")
            ?.trim()
            ?.takeIf { it.isNotEmpty() }

    val authorizationHeader: String?
        get() = accessToken?.let { "Bearer $it" }

    val userId: Int
        get() = preferencesOrThrow().getInt(USER_ID_KEY, 0)

    val userRole: String?
        get() = preferencesOrThrow().getString(USER_ROLE_KEY, null)

    val hasActiveSession: Boolean
        get() = accessToken != null

    fun saveSession(accessToken: String, userId: Int, userRole: String?) {
        val cleanToken = accessToken.removePrefix("Bearer ").trim()
        require(cleanToken.isNotEmpty()) { "El token de sesion no puede estar vacio" }

        preferencesOrThrow().edit()
            .putString(ACCESS_TOKEN_KEY, cleanToken)
            .putInt(USER_ID_KEY, userId)
            .putString(USER_ROLE_KEY, userRole)
            .apply()
    }

    fun clearSession() {
        preferencesOrThrow().edit()
            .remove(ACCESS_TOKEN_KEY)
            .remove(USER_ID_KEY)
            .remove(USER_ROLE_KEY)
            .apply()
    }

    private fun preferencesOrThrow(): SharedPreferences = checkNotNull(preferences) {
        "SessionManager debe inicializarse antes de acceder a la sesion"
    }
}
