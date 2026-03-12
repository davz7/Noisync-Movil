package mx.edu.noisync.ui.visitor

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.noisync.R
import mx.edu.noisync.data.fake.FakeSongs
import mx.edu.noisync.ui.components.TransposeButton

@Composable
fun SongDetailScreen(navController: NavController, songId: String?) {
    val song = FakeSongs.findSongById(songId)
    val previewDetails = FakeSongs.getPreviewDetails(songId)

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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Volver", color = Color.Black, fontWeight = FontWeight.Medium)
                }
            }
            Surface(
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 2.dp,
                color = Color(0xFFF4F5F6),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.undefined),
                            contentDescription = "Song Image",
                            modifier = Modifier.size(70.dp)
                        )
                        Column(modifier = Modifier.padding(start = 12.dp)) {
                            Text(
                                text = song?.title ?: "Cancion no disponible",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = song?.bandName ?: "Sin banda",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )

                            Row(modifier = Modifier.padding(top = 4.dp)) {
                                Text(text = "Tono: ${previewDetails.tone}", fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(text = "BPM: ${previewDetails.bpm}", fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Transposicion",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TransposeButton(text = "-1", subText = "") { }
                        TransposeButton(text = "-", subText = "1/2") { }

                        Surface(
                            onClick = { },
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

                        TransposeButton(text = "+", subText = "1/2") { }
                        TransposeButton(text = "+1", subText = "") { }
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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(previewDetails.lines) { line ->
                        Text(text = line, color = Color.Gray)
                    }
                }
            }
        }
    }
}
