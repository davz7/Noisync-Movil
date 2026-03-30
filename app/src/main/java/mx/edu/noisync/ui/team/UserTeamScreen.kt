package mx.edu.noisync.ui.team

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.noisync.data.model.Musician
import mx.edu.noisync.ui.components.AuthenticatedBottomBar
import mx.edu.noisync.ui.components.AuthenticatedDestination

@Composable
fun UserTeamScreen(
    navController: NavController,
    onOpenSongs: () -> Unit,
    onOpenTeam: () -> Unit,
    onOpenInstruments: () -> Unit,
    onOpenProfile: () -> Unit
) {
    val viewModel: TeamViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F9FA)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Musicos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                when (val state = uiState) {
                    TeamUiState.Loading -> {
                        CircularProgressIndicator(color = Color.Black)
                    }

                    is TeamUiState.Error -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(text = state.message, color = Color.Gray)
                            Surface(
                                onClick = viewModel::loadMusicians,
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

                    is TeamUiState.Success -> {
                        if (state.musicians.isEmpty()) {
                            Text(text = "No hay musicos disponibles.", color = Color.Gray)
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(state.musicians) { musician ->
                                    MusicianCard(musician = musician)
                                }

                                item { Spacer(modifier = Modifier.height(20.dp)) }
                            }
                        }
                    }
                }
            }
            AuthenticatedBottomBar(
                selectedDestination = AuthenticatedDestination.TEAM,
                onOpenSongs = onOpenSongs,
                onOpenTeam = onOpenTeam,
                onOpenInstruments = onOpenInstruments,
                onOpenProfile = onOpenProfile
            )
        }
    }
}

@Composable
private fun MusicianCard(musician: Musician) {
    val statusLabel = musician.status
        ?.lowercase()
        ?.replaceFirstChar { it.uppercase() }
        ?: "Sin estado"
    val instrumentsLabel = musician.instruments.joinToString(", ").ifBlank { "Sin instrumentos" }
    val usernameLabel = musician.username?.takeIf { it.isNotBlank() }?.let { "@$it" } ?: "Sin usuario"
    val (badgeBgColor, badgeTextColor) = when (musician.status?.uppercase()) {
        "ACTIVO" -> Color(0xFFD1FAE5) to Color(0xFF059669)
        else -> Color(0xFFFEF3C7) to Color(0xFFD97706)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(colorFromText(musician.fullName), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initialsFromName(musician.fullName),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = musician.fullName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = instrumentsLabel,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
                Text(
                    text = usernameLabel,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Surface(
                color = badgeBgColor,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = statusLabel,
                    color = badgeTextColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

private fun initialsFromName(name: String): String {
    val words = name.trim().split(Regex("\\s+")).filter { it.isNotBlank() }
    return when {
        words.isEmpty() -> "NS"
        words.size == 1 -> words.first().take(2).uppercase()
        else -> (words[0].take(1) + words[1].take(1)).uppercase()
    }
}

private fun colorFromText(text: String): Color {
    var hash = 0
    for (character in text) {
        hash = character.code + ((hash shl 5) - hash)
    }
    val hue = kotlin.math.abs(hash % 360)
    return hslColor(hue.toFloat(), 0.60f, 0.55f)
}

private fun hslColor(hue: Float, saturation: Float, lightness: Float): Color {
    val c = (1f - kotlin.math.abs(2f * lightness - 1f)) * saturation
    val x = c * (1f - kotlin.math.abs((hue / 60f) % 2f - 1f))
    val m = lightness - c / 2f

    val (r1, g1, b1) = when {
        hue < 60f -> Triple(c, x, 0f)
        hue < 120f -> Triple(x, c, 0f)
        hue < 180f -> Triple(0f, c, x)
        hue < 240f -> Triple(0f, x, c)
        hue < 300f -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }

    return Color(red = r1 + m, green = g1 + m, blue = b1 + m, alpha = 1f)
}
