package mx.edu.noisync.ui.instruments

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import mx.edu.noisync.data.model.Instrument
import mx.edu.noisync.ui.components.AuthenticatedBottomBar
import mx.edu.noisync.ui.components.AuthenticatedDestination

@Composable
fun UserInstrumentsScreen(
    navController: NavController,
    onOpenSongs: () -> Unit,
    onOpenMySongs: () -> Unit,
    onOpenTeam: () -> Unit,
    onOpenInstruments: () -> Unit,
    onOpenProfile: () -> Unit
) {
    val viewModel: InstrumentsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

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
                text = "Instrumentos",
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
                InstrumentsUiState.Loading -> {
                    CircularProgressIndicator(color = Color.Black)
                }

                is InstrumentsUiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = state.message, color = Color.Gray)
                        Surface(
                            onClick = viewModel::loadInstruments,
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

                is InstrumentsUiState.Success -> {
                    if (state.instruments.isEmpty()) {
                        Text(text = "No hay instrumentos disponibles.", color = Color.Gray)
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(state.instruments) { instrument ->
                                InstrumentCard(instrument = instrument)
                            }

                            item { Spacer(modifier = Modifier.height(20.dp)) }
                        }
                    }
                }
            }
        }
        AuthenticatedBottomBar(
            selectedDestination = AuthenticatedDestination.INSTRUMENTS,
            onOpenSongs = onOpenSongs,
            onOpenMySongs = onOpenMySongs,
            onOpenTeam = onOpenTeam,
            onOpenInstruments = onOpenInstruments,
            onOpenProfile = onOpenProfile
        )
    }
}

@Composable
private fun InstrumentCard(instrument: Instrument) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp
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
                    .background(colorFromText(instrument.name), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = instrument.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "${instrument.musiciansCount} musicos asignados",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(0xFFF1F5F9), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = instrument.musiciansCount.toString(),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
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
