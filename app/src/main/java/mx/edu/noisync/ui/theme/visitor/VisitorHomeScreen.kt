package mx.edu.noisync.ui.theme.visitor

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.noisync.R
import mx.edu.noisync.model.Song
import mx.edu.noisync.ui.theme.components.SongCard

@Composable
fun VisitorHomeScreen(songs: List<Song>, onOpenSong: (Song) -> Unit ){
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Noisync"
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painterResource(R.drawable.boton_de_lista),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(20.dp)
                    //Tengo que hacerlo clickeable

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "Explorar canciones publicas",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Surface(
            shape = RoundedCornerShape(10.dp),
            shadowElevation = 1.dp,
            color = Color(246, 247, 248, 255),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painterResource(R.drawable.simbolo_de_la_interfaz_de_busqueda),
                    contentDescription = "Busqueda",
                    modifier = Modifier
                        .size(20.dp)

                )
                Text(
                    text = "Buscar por titulo, artista o genero",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(5.dp)
        ) {
            Surface(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp),
                shadowElevation = 1.dp,
                color = Color(0xFFF4F5F6),
                modifier = Modifier
            ) {
                Text(
                    text = "Todas",
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

            Surface(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp),
                shadowElevation = 1.dp,
                color = Color(0xFFF4F5F6)
            ) {
                Text(
                    text = "Recientes",
                    modifier = Modifier
                    .padding(10.dp)
                )
            }
        }
        Surface(
            shape = RoundedCornerShape(10.dp),
            shadowElevation = 1.dp,
            color = Color(246, 247, 248, 255),
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Usamos la versión de "items" que recibe la lista directamente
                items(songs) { song ->
                    SongCard(song = song, onOpen = { onOpenSong(song) })
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE8E3E3),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Cargar mas"
                )
            }
        }
    }
}