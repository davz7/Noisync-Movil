package mx.edu.noisync.ui.songs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.noisync.data.model.SongDetail
import mx.edu.noisync.ui.components.BackButton
import mx.edu.noisync.ui.components.TransposeButton

@Composable
fun SongDetailScreen(
    navController: NavController,
    songId: String?,
    isPublicSong: Boolean = false
) {
    val viewModel: SongDetailViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(songId, isPublicSong) {
        viewModel.loadSong(songId, isPublicSong)
    }

    when (val state = uiState) {
        is SongDetailUiState.Success -> {
            SongDetailContent(
                song = state.song,
                onBack = { navController.popBackStack() }
            )
        }

        is SongDetailUiState.Error -> {
            SongDetailStatusContent(
                message = state.message,
                onBack = { navController.popBackStack() },
                onRetry = { viewModel.loadSong(songId, isPublicSong) }
            )
        }

        SongDetailUiState.Idle, SongDetailUiState.Loading -> {
            SongDetailLoadingContent(onBack = { navController.popBackStack() })
        }
    }
}

@Composable
private fun SongDetailContent(
    song: SongDetail,
    onBack: () -> Unit
) {
    var transposition by rememberSaveable(song.id) { mutableIntStateOf(0) }
    val displayedKey = transposeKey(song.originalKey, transposition)
    val transpositionLabel = if (transposition == 0) "Original" else "${if (transposition > 0) "+" else ""}$transposition st"

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
            BackButton(onClick = onBack)
            Surface(
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 2.dp,
                color = Color(0xFFF4F5F6),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        SongCover(
                            title = song.title,
                            coverUrl = song.coverUrl,
                            modifier = Modifier.size(70.dp),
                            shape = RoundedCornerShape(8.dp),
                            initialsSize = 24.sp
                        )
                        Column(modifier = Modifier.padding(start = 12.dp)) {
                            Text(
                                text = song.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = song.artistName,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                            Text(
                                text = song.bandName ?: "Sin banda",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )

                            Row(modifier = Modifier.padding(top = 4.dp)) {
                                Text(text = "Tono: $displayedKey", fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(text = "BPM: ${song.bpm}", fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "Controles de transposicion",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (transposition == 0) Color(0xFF6B7280) else Color(0xFF198754)
                        ) {
                            Text(
                                text = transpositionLabel,
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TransposeButton(text = "-1", subText = "") { transposition -= 2 }
                        TransposeButton(text = "-", subText = "1/2") { transposition -= 1 }

                        Surface(
                            onClick = { transposition = 0 },
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White,
                            shadowElevation = 1.dp,
                            modifier = Modifier.size(55.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Reset Icon"
                                )
                            }
                        }

                        TransposeButton(text = "+", subText = "1/2") { transposition += 1 }
                        TransposeButton(text = "+1", subText = "") { transposition += 2 }
                    }
                }
            }
            Surface(
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 1.dp,
                color = Color(0xFFF6F7F8),
                modifier = Modifier
                    .padding(top = 15.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        SongStructureView(
                            sections = song.sections,
                            tonic = song.originalKey,
                            scale = song.baseScale,
                            transposition = transposition
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SongDetailLoadingContent(onBack: () -> Unit) {
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
            BackButton(onClick = onBack)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }
}

@Composable
private fun SongDetailStatusContent(
    message: String,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
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
            BackButton(onClick = onBack)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = message, color = Color.Gray)
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
    }
}
