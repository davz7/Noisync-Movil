package mx.edu.noisync.core.network

import android.content.Context
import mx.edu.noisync.core.session.SessionExpirationManager
import mx.edu.noisync.data.local.SessionManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class SessionExpirationAuthenticator(context: Context) : Authenticator {
    private val sessionManager = SessionManager(context.applicationContext)

    override fun authenticate(route: Route?, response: Response): Request? {
        val hadAuthorization = response.request.header("Authorization") != null
        val hasSession = sessionManager.getAccessToken() != null

        if (!hadAuthorization || !hasSession) {
            return null
        }

        sessionManager.clearSession()
        SessionExpirationManager.notifySessionExpired()
        return null
    }
}
