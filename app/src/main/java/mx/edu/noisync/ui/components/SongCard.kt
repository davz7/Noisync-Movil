package mx.edu.noisync.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.noisync.R
import mx.edu.noisync.model.Song

@Composable
fun SongCard(song: Song, onOpen: () -> Unit){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F3F4)
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(R.drawable.undefined),
                contentDescription = "Song Image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(4.dp))
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
                    text = song.bandName,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (song.isPublic){
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                shadowElevation = 1.dp,
                                color = Color(0xFFDFF5E1),
                                modifier = Modifier
                                    .animateContentSize()
                                    .padding(1.dp)
                            ) {
                                Text(
                                    text = "Publica",
                                    fontSize = 12.sp,
                                    color = Color(0xFF006400),
                                    modifier = Modifier
                                        .padding(5.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Surface(
                            onClick = { /*TODO*/ },
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
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                        }
                    }
            }
        }
    }
}


@Preview
@Composable
fun SongCardPreview(){
    SongCard(song = Song(id = "1", title = "Song name", bandName = "Band name", isPublic = true), onOpen = {})
}

