package mx.edu.noisync.ui.profile

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.noisync.MainActivity
import mx.edu.noisync.data.local.SessionManager
import mx.edu.noisync.data.model.UserProfile
import mx.edu.noisync.ui.components.AuthenticatedBottomBar
import mx.edu.noisync.ui.components.AuthenticatedDestination

@Composable
fun UserInfo(
    navController: NavController,
    onOpenSongs: () -> Unit,
    onOpenMySongs: () -> Unit,
    onOpenTeam: () -> Unit,
    onOpenInstruments: () -> Unit,
    onOpenProfile: () -> Unit
) {
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val viewModel: UserInfoViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val errorMessage = (uiState as? UserInfoUiState.Error)?.message

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    when (val state = uiState) {
        UserInfoUiState.Loading -> {
            UserInfoLoadingContent(
                onOpenSongs = onOpenSongs,
                onOpenMySongs = onOpenMySongs,
                onOpenTeam = onOpenTeam,
                onOpenInstruments = onOpenInstruments,
                onOpenProfile = onOpenProfile
            )
        }

        is UserInfoUiState.Success -> {
            val profile = state.profile
            UserInfoContent(
                sessionManager = sessionManager,
                displayName = profile.displayName(),
                displayRole = profile.roleLabel(sessionManager.getRole()),
                displayBand = profile.bandName ?: "Sin banda",
                displayEmail = profile.email ?: "No disponible",
                onOpenSongs = onOpenSongs,
                onOpenMySongs = onOpenMySongs,
                onOpenTeam = onOpenTeam,
                onOpenInstruments = onOpenInstruments,
                onOpenProfile = onOpenProfile
            )
        }

        is UserInfoUiState.Error -> {
            UserInfoErrorContent(
                onRetry = viewModel::loadProfile,
                onOpenSongs = onOpenSongs,
                onOpenMySongs = onOpenMySongs,
                onOpenTeam = onOpenTeam,
                onOpenInstruments = onOpenInstruments,
                onOpenProfile = onOpenProfile
            )
        }
    }
}

@Composable
private fun UserInfoContent(
    sessionManager: SessionManager,
    displayName: String,
    displayRole: String,
    displayBand: String,
    displayEmail: String,
    onOpenSongs: () -> Unit,
    onOpenMySongs: () -> Unit,
    onOpenTeam: () -> Unit,
    onOpenInstruments: () -> Unit,
    onOpenProfile: () -> Unit
) {
    val context = LocalContext.current
    val initials = displayName
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.take(1).uppercase() }
        .ifBlank { "NS" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 16.dp),
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
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFF8B5CF6), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = displayName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "$displayRole | $displayBand",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Correo electronico",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = displayEmail,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp),
                    fontWeight = FontWeight.Normal
                )
            }

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
                    text = displayRole,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp),
                    fontWeight = FontWeight.Normal
                )
            }

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
                    text = displayBand,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp),
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                onClick = {
                    sessionManager.clearSession()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                },
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
                        text = "Cerrar sesion",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }

        AuthenticatedBottomBar(
            selectedDestination = AuthenticatedDestination.PROFILE,
            onOpenSongs = onOpenSongs,
            onOpenMySongs = onOpenMySongs,
            onOpenTeam = onOpenTeam,
            onOpenInstruments = onOpenInstruments,
            onOpenProfile = onOpenProfile
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    UserInfoContent(
        sessionManager = SessionManager(LocalContext.current),
        displayName = "Juan Delgado",
        displayRole = "Lider",
        displayBand = "Los Nocturnos",
        displayEmail = "juan.delgado@example.com",
        onOpenSongs = {},
        onOpenMySongs = {},
        onOpenTeam = {},
        onOpenInstruments = {},
        onOpenProfile = {}
    )
}

@Composable
private fun UserInfoLoadingContent(
    onOpenSongs: () -> Unit,
    onOpenMySongs: () -> Unit,
    onOpenTeam: () -> Unit,
    onOpenInstruments: () -> Unit,
    onOpenProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Black)
        }

        AuthenticatedBottomBar(
            selectedDestination = AuthenticatedDestination.PROFILE,
            onOpenSongs = onOpenSongs,
            onOpenMySongs = onOpenMySongs,
            onOpenTeam = onOpenTeam,
            onOpenInstruments = onOpenInstruments,
            onOpenProfile = onOpenProfile
        )
    }
}

@Composable
private fun UserInfoErrorContent(
    onRetry: () -> Unit,
    onOpenSongs: () -> Unit,
    onOpenMySongs: () -> Unit,
    onOpenTeam: () -> Unit,
    onOpenInstruments: () -> Unit,
    onOpenProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "No se pudo cargar el perfil.",
                        color = Color.Gray
                    )
                    Surface(
                        onClick = onRetry,
                        shape = RoundedCornerShape(10.dp),
                        shadowElevation = 1.dp,
                        color = Color.Black
                    ) {
                        Text(
                            text = "Reintentar",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                        )
                    }
                }
            }
        }
        AuthenticatedBottomBar(
            selectedDestination = AuthenticatedDestination.PROFILE,
            onOpenSongs = onOpenSongs,
            onOpenMySongs = onOpenMySongs,
            onOpenTeam = onOpenTeam,
            onOpenInstruments = onOpenInstruments,
            onOpenProfile = onOpenProfile
        )
    }
}

private fun UserProfile?.displayName(): String {
    return this?.fullName
        ?.takeIf { it.isNotBlank() }
        ?: this?.username?.takeIf { it.isNotBlank() }
        ?: "Usuario Noisync"
}

private fun UserProfile?.roleLabel(sessionRole: String?): String {
    val role = this?.role ?: sessionRole
    return when (role?.uppercase()) {
        "LEADER" -> "Lider"
        "MUSICIAN" -> "Musico"
        else -> "Usuario"
    }
}
