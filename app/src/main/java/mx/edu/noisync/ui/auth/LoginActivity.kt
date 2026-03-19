package mx.edu.noisync.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import mx.edu.noisync.MainActivity
import mx.edu.noisync.core.network.RetrofitClient
import mx.edu.noisync.data.local.SessionManager
import mx.edu.noisync.core.theme.NoisyncTheme

class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        RetrofitClient.init(applicationContext)
        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            routeFromSession()
            return
        }

        setContent {
            NoisyncTheme {
                // Creamos un navController aquí para que LoginScreen pueda usar el BackButton
                val navController = rememberNavController()
                val uiState by viewModel.uiState.collectAsState()

                LoginScreen(
                    navController = navController,
                    uiState = uiState,
                    onLoginClick = { user, pass -> viewModel.login(user, pass) }
                )
            }
        }

        // Observar cambios de estado para navegación y Toasts
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is LoginUiState.Success -> {
                        sessionManager.saveSession(state.data)
                        routeFromSession()
                    }
                    is LoginUiState.Error -> {
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun routeFromSession() {
        val destination = if (sessionManager.mustChangePassword()) {
            ChangePasswordActivity::class.java
        } else {
            MainActivity::class.java
        }
        startActivity(Intent(this, destination).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }
}
