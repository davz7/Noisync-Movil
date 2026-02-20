package mx.edu.noisync.ui.theme.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Bullet
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.noisync.R
import mx.edu.noisync.model.Song

@Composable
fun SongCard(song: Song, onOpen: () -> Unit){
    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(R.drawable.masterofpuppets),
                contentDescription = "Song Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = song.title,
                    fontSize = 16.sp
                )
                Text(
                    text = song.bandName,
                    fontSize = 12.sp
                )
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    color = Color(0xFFDFF5E1),
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {
                    Text(
                        song.isPublic.toString(),
                        fontSize = 12.sp,
                        color = Color(0xFF006400),
                        modifier = Modifier
                            .padding(3.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onOpen,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = "Open",
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun SongCardPreview(){
    SongCard(song = Song(id = "1", title = "Master Of Puppets - Remastered", bandName = "Metalica", isPublic = true), onOpen = {})
}