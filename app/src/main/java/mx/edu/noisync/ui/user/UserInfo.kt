package mx.edu.noisync.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun UserInfo(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Color.White)
    ) {
        // --- BOTÓN VOLVER ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.Transparent, // Fondo transparente como solicitaste
                onClick = { navController.popBackStack() }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Volver",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // --- HEADER ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Mi Perfil",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar con Iniciales
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFF8B5CF6), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "JD",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre del Usuario
            Text(
                text = "Juan Delgado",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Rol y Banda
            Text(
                text = "Líder • Los Nocturnos",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))

            // Detalles de Información - Correo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Correo electrónico",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "juan.delgado@example.com",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp),
                    fontWeight = FontWeight.Normal
                )
            }

            // Detalles de Información - Rol
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Rol en la banda",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Líder",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp),
                    fontWeight = FontWeight.Normal
                )
            }

            // Detalles de Información - Banda
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Banda",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Los Nocturnos",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp),
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
            Spacer(modifier = Modifier.height(32.dp))

            // Botón Cerrar Sesión
            Surface(
                onClick = { /* TODO: Implementar logout */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF8F9FA)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Cerrar sesión",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    UserInfo(rememberNavController())
}
