package mx.edu.noisync.data.local

import android.content.Context
import android.content.SharedPreferences
import mx.edu.noisync.data.model.LoginResponse

class SessionManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )
    
    fun saveSession(response: LoginResponse) {
        prefs.edit()
            .putString(KEY_ACCESS_TOKEN, response.accessToken)
            .putString(KEY_REFRESH_TOKEN, response.refreshToken)
            .putLong(KEY_USER_ID, response.userId)
            .putLong(KEY_BAND_ID, response.bandId)
            .putString(KEY_ROLE, response.role)
            .putBoolean(KEY_MUST_CHANGE_PASSWORD, response.mustChangePassword)
            .apply()
    }
    
    fun getAccessToken(): String? {
        return prefs.getString(KEY_ACCESS_TOKEN, null)
    }
    
    fun getRefreshToken(): String? {
        return prefs.getString(KEY_REFRESH_TOKEN, null)
    }
    
    fun getUserId(): Long {
        return prefs.getLong(KEY_USER_ID, -1)
    }
    
    fun getBandId(): Long {
        return prefs.getLong(KEY_BAND_ID, -1)
    }
    
    fun getRole(): String? {
        return prefs.getString(KEY_ROLE, null)
    }
    
    fun mustChangePassword(): Boolean {
        return prefs.getBoolean(KEY_MUST_CHANGE_PASSWORD, false)
    }
    
    fun isLoggedIn(): Boolean {
        return getAccessToken() != null
    }
    
    fun clearSession() {
        prefs.edit().clear().apply()
    }
    
    companion object {
        private const val PREFS_NAME = "app_prefs"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_BAND_ID = "band_id"
        private const val KEY_ROLE = "role"
        private const val KEY_MUST_CHANGE_PASSWORD = "must_change_password"
    }
}
