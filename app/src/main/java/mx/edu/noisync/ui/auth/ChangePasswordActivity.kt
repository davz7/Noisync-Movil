package mx.edu.noisync.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.noisync.MainActivity
import mx.edu.noisync.data.local.SessionManager
import mx.edu.noisync.ui.login.LoginActivity
import mx.edu.noisync.ui.theme.NoisyncTheme

class ChangePasswordActivity : ComponentActivity() {
    private val viewModel: ChangePasswordViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            goToLogin()
            return
        }

        if (!sessionManager.mustChangePassword()) {
            goToHome()
            return
        }

        setContent {
            NoisyncTheme {
                ChangePasswordScreen(
                    viewModel = viewModel,
                    onLogout = {
                        sessionManager.clearSession()
                        goToLogin()
                    }
                )
            }
        }
    }

    @Composable
    private fun ChangePasswordScreen(
        viewModel: ChangePasswordViewModel,
        onLogout: () -> Unit
    ) {
        var currentPassword by remember { mutableStateOf("") }
        var newPassword by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        val uiState by viewModel.uiState.collectAsState()
        val errorMessage = (uiState as? ChangePasswordUiState.Error)?.message

        LaunchedEffect(errorMessage) {
            if (errorMessage != null) {
                Toast.makeText(this@ChangePasswordActivity, errorMessage, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
        }

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
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .padding(20.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "Cambiar contrasena",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Debes actualizar tu contrasena temporal para continuar",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.padding(15.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Contrasena actual",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )

                            OutlinedTextField(
                                value = currentPassword,
                                onValueChange = { currentPassword = it },
                                placeholder = { Text("Ingresa tu contrasena temporal") },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            Text(
                                text = "Nueva contrasena",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )

                            OutlinedTextField(
                                value = newPassword,
                                onValueChange = { newPassword = it },
                                placeholder = { Text("Ingresa una contrasena nueva") },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            Text(
                                text = "Confirmar contrasena",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )

                            OutlinedTextField(
                                value = confirmPassword,
                                onValueChange = { confirmPassword = it },
                                placeholder = { Text("Repite tu nueva contrasena") },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )

                            Spacer(modifier = Modifier.padding(12.dp))

                            Surface(
                                onClick = {
                                    viewModel.submit(
                                        currentPassword = currentPassword,
                                        newPassword = newPassword,
                                        confirmPassword = confirmPassword
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                shadowElevation = 1.dp,
                                color = Color(0xFF212529),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(15.dp)
                                ) {
                                    Text(
                                        text = "Actualizar contrasena",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.padding(8.dp))

                            Surface(
                                onClick = onLogout,
                                shape = RoundedCornerShape(10.dp),
                                shadowElevation = 1.dp,
                                color = Color(0xFFF8F9FA),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(15.dp)
                                ) {
                                    Text(
                                        text = "Cerrar sesion",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))
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

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
