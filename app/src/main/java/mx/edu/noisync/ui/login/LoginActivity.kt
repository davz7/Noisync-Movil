package mx.edu.noisync.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.edu.noisync.MainActivity
import mx.edu.noisync.data.local.SessionManager
import mx.edu.noisync.ui.theme.NoisyncTheme

class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        sessionManager = SessionManager(this)
        
        if (sessionManager.isLoggedIn()) {
            if (sessionManager.mustChangePassword()) {
                goToChangePassword()
            } else {
                goToHome()
            }
            return
        }
        
        setContent {
            NoisyncTheme {
                LoginScreen()
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is LoginUiState.Idle -> Unit
                    is LoginUiState.Loading -> Unit
                    is LoginUiState.Success -> {
                        sessionManager.saveSession(state.data)
                        
                        if (state.data.mustChangePassword) {
                            goToChangePassword()
                        } else {
                            goToHome()
                        }
                    }
                    is LoginUiState.Error -> {
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    @Composable
    fun LoginScreen() {
        var identifier by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val uiState by viewModel.uiState.collectAsState()
        val isLoading = uiState is LoginUiState.Loading

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(15.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Iniciar sesión",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Accede a tu cuenta Noisync",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.padding(15.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Correo o usuario",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                            
                            OutlinedTextField(
                                value = identifier,
                                onValueChange = { identifier = it },
                                placeholder = { Text("usuario o tu@email.com") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !isLoading,
                                shape = RoundedCornerShape(10.dp)
                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            Text(
                                text = "Contraseña",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                            
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                placeholder = { Text("Ingresa contraseña") },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !isLoading,
                                shape = RoundedCornerShape(10.dp)
                            )

                            Spacer(modifier = Modifier.padding(12.dp))

                            Surface(
                                onClick = { 
                                    if (!isLoading) {
                                        viewModel.login(identifier, password)
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                shadowElevation = 1.dp,
                                color = if (isLoading) Color.Gray else Color(0xFF212529),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(15.dp)
                                ) {
                                    if (isLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(20.dp),
                                            color = Color.White,
                                            strokeWidth = 2.dp
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = "Cargando...",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.White
                                        )
                                    } else {
                                        Text(
                                            text = "Entrar",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.padding(12.dp))
                        Text(
                            text = "¿Olvidaste tu contraseña?",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "¿No tienes una cuenta? Regístrate",
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }

    private fun goToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun goToChangePassword() {
        Toast.makeText(this, "Debes cambiar tu contraseña", Toast.LENGTH_LONG).show()
        goToHome()
    }
}
