package mx.edu.noisync.ui.auth

import android.app.Activity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.noisync.ui.components.BackButton

@Composable
fun LoginScreen(
    navController: NavController,
    uiState: LoginUiState,
    onLoginClick: (String, String) -> Unit
) {
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isLoading = uiState is LoginUiState.Loading
    val context = LocalContext.current

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
            BackButton(onClick = { 
                if (!navController.popBackStack()) {
                    (context as? Activity)?.finish()
                }
            })
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

                    Text(text = "Iniciar sesión", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Accede a tu cuenta Noisync", color = Color.Gray, fontSize = 14.sp)

                    Spacer(modifier = Modifier.padding(15.dp))

                    OutlinedTextField(
                        value = identifier,
                        onValueChange = { identifier = it },
                        label = { Text("Correo o usuario") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.padding(12.dp))

                    Button(
                        onClick = { onLoginClick(identifier, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF212529))
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Entrar", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    TextButton(onClick = { /* TODO */ }) {
                        Text("¿Olvidaste tu contraseña?", color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
