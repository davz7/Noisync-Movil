package mx.edu.noisync

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.edu.noisync.core.session.installSessionExpirationHandler
import mx.edu.noisync.data.local.SessionManager
import mx.edu.noisync.ui.auth.ChangePasswordActivity
import mx.edu.noisync.ui.navigation.LeaderNavigation
import mx.edu.noisync.ui.navigation.MusicianNavigation
import mx.edu.noisync.ui.navigation.VisitorNavigation
import mx.edu.noisync.core.theme.NoisyncTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSessionExpirationHandler()

        val sessionManager = SessionManager(this)
        val role = sessionManager.getRole()

        if (sessionManager.isLoggedIn() && sessionManager.mustChangePassword()) {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
            finish()
            return
        }

        setContent {
            NoisyncTheme {
                when (role) {
                    "MUSICIAN" -> MusicianNavigation()
                    "LEADER" -> LeaderNavigation()
                    else -> VisitorNavigation()
                }
            }
        }
    }
}
