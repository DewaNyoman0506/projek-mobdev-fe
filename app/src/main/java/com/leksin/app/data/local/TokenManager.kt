package com.leksin.app.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Menyimpan sesi login (JWT + info user!!)
 *
 * - Disimpan terenkripsi (Keystore-backed). TIDAK ada fallback ke plaintext.
 * - Kalau Keystore/prefs rusak: file prefs direset sekali lalu dicoba lagi.
 *   Kalau tetap gagal, sesi hanya hidup di memori (user login ulang setelah
 *   app ditutup) -- lebih baik daripada menyimpan token sebagai plaintext.
 * - Sesi di-cache di memori, jadi AuthInterceptor tidak perlu mendekripsi
 *   prefs di setiap request.
 */
class TokenManager(context: Context) {

    private data class Session(
        val token: String,
        val userId: String,
        val username: String,
        val role: String,
        val expiresAtMillis: Long,
    )

    private val prefs: SharedPreferences? = openEncryptedPrefs(context.applicationContext)

    @Volatile
    private var session: Session? = prefs?.let { readSession(it) }

    fun saveAuthSession(
        token: String,
        userId: String,
        username: String,
        role: String,
        expiresInSeconds: Long
    ) {
        val newSession = Session(
            token = token,
            userId = userId,
            username = username,
            role = role,
            expiresAtMillis = System.currentTimeMillis() + expiresInSeconds * 1000,
        )
        session = newSession

        try {
            prefs?.edit()?.let {
                it.putString(KEY_TOKEN, newSession.token)
                it.putString(KEY_USER_ID, newSession.userId)
                it.putString(KEY_USERNAME, newSession.username)
                it.putString(KEY_ROLE, newSession.role)
                it.putLong(KEY_EXPIRES_AT, newSession.expiresAtMillis)
                it.apply()
            }
        } catch (e: Exception) {
            // Sesi tetap valid di memori
        }
    }

    fun getToken(): String? = session?.token

    fun getUserId(): String? = session?.userId

    fun getUsername(): String? = session?.username

    fun getRole(): String? = session?.role

    fun isTokenExpired(): Boolean {
        val current = session ?: return false
        return System.currentTimeMillis() >= current.expiresAtMillis
    }

    fun isLoggedIn(): Boolean {
        val current = session ?: return false
        return current.token.isNotBlank() && !isTokenExpired()
    }

    fun clearSession() {
        session = null
        try {
            prefs?.edit()?.clear()?.apply()
        } catch (e: Exception) {
        }
    }

    private fun readSession(p: SharedPreferences): Session? {
        return try {
            val token = p.getString(KEY_TOKEN, null)
            if (token.isNullOrBlank()) return null
            Session(
                token = token,
                userId = p.getString(KEY_USER_ID, null).orEmpty(),
                username = p.getString(KEY_USERNAME, null).orEmpty(),
                role = p.getString(KEY_ROLE, null).orEmpty(),
                expiresAtMillis = p.getLong(KEY_EXPIRES_AT, 0L),
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun openEncryptedPrefs(context: Context): SharedPreferences? {
        repeat(2) { attempt ->
            try {
                return createEncryptedPrefs(context)
            } catch (e: Exception) {
                if (attempt == 0) context.deleteSharedPreferences(PREFS_FILENAME)
            }
        }
        return null
    }

    private fun createEncryptedPrefs(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFS_FILENAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        private const val PREFS_FILENAME = "leksin_secure_prefs"
        private const val KEY_TOKEN = "access_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_ROLE = "role"
        private const val KEY_EXPIRES_AT = "expires_at"
    }
}