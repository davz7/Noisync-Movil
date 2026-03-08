package mx.edu.noisync.ui.auth

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
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
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.Transparent,
                onClick = { navController.popBackStack() }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Volver", color = Color.Black, fontWeight = FontWeight.Medium)
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(20.dp)
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
                        Text(text = "Correo o usuario", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        InputChip(
                            onClick = { /*TODO*/ },
                            label = { Text("usuario o tu@email.com") },
                            selected = false,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.padding(8.dp))

                        Text(text = "Contraseña", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        InputChip(
                            onClick = { /*TODO*/ },
                            label = { Text("Ingresa contraseña") },
                            selected = false,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.padding(12.dp))

                        Surface(
                            onClick = { },
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
                                    text = "Entrar",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(text = "¿Olvidaste tu contraseña?", color = Color.Gray, fontSize = 13.sp)
                    Text(text = "¿No tienes una cuenta? Regístrate", fontWeight = FontWeight.Medium, fontSize = 13.sp)
                }
            }
        }
    }
}
