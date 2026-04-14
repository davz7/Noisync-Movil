package mx.edu.noisync.core.session

import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import mx.edu.noisync.ui.auth.LoginActivity

fun ComponentActivity.installSessionExpirationHandler() {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            SessionExpirationManager.events.collect {
                Toast.makeText(
                    this@installSessionExpirationHandler,
                    "Tu sesion expiro. Inicia sesion otra vez.",
                    Toast.LENGTH_LONG
                ).show()

                startActivity(Intent(this@installSessionExpirationHandler, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                finish()
            }
        }
    }
}
