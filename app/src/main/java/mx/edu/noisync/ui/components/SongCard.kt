package mx.edu.noisync.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.noisync.data.model.SongListItem

@Composable
fun SongCard(song: SongListItem, onOpen: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(10.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            SongCover(
                title = song.title,
                coverUrl = song.coverUrl,
                modifier = Modifier
                    .size(50.dp),
                shape = RoundedCornerShape(4.dp),
                initialsSize = 18.sp
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = song.title,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artistName,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        shadowElevation = 1.dp,
                        color = if (song.isPublic) Color(0xFFDFF5E1) else Color(0xFFF3E8FF),
                        modifier = Modifier
                            .animateContentSize()
                            .padding(1.dp)
                    ) {
                        Text(
                            text = if (song.isPublic) "Publica" else "Privada",
                            fontSize = 12.sp,
                            color = if (song.isPublic) Color(0xFF006400) else Color(0xFF6B21A8),
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        onClick = onOpen,
                        shape = RoundedCornerShape(10.dp),
                        shadowElevation = 1.dp,
                        color = Color.Black,
                        modifier = Modifier
                            .animateContentSize()
                            .padding(1.dp)
                    ) {
                        Text(
                            text = "Abrir",
                            fontSize = 12.sp,
                            color = Color.White,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}
