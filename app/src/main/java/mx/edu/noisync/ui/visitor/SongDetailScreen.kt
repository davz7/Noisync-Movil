package mx.edu.noisync.ui.visitor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.noisync.R
import mx.edu.noisync.ui.components.TransposeButton

@Composable
fun SongDetailScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
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
                            Text(text = "Song name", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(text = "Band name", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                            
                            Row(modifier = Modifier.padding(top = 4.dp)) {
                                Text(text = "Tono: G", fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(text = "BPM: 120", fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Transposición",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Botones creados desde cero
                        TransposeButton(text = "-1", subText = "") { /* TODO */ }
                        TransposeButton(text = "-", subText = "½") { /* TODO */ }
                        
                        // Botón de Reset (Central)
                        Surface(
                            onClick = { /* TODO */ },
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White,
                            shadowElevation = 1.dp,
                            modifier = Modifier.size(55.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Reset Icon",
                                )
                            }
                        }

                        TransposeButton(text = "+", subText = "½") { /* TODO */ }
                        TransposeButton(text = "+1", subText = "") { /* TODO */ }
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
                    modifier = Modifier.fillMaxSize().padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item { Text("Aquí van las notas de la canción...", color = Color.Gray) }
                }
            }
        }
    }
}
