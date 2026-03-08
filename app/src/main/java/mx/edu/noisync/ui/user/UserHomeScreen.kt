package mx.edu.noisync.ui.user

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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.noisync.R
import mx.edu.noisync.model.Song
import mx.edu.noisync.ui.components.SongCard
import mx.edu.noisync.ui.navigation.AppsScreens

@Composable
fun UserHomeScreen(songs: List<Song>, onOpenSong: (Song) -> Unit? ){
    var expanded by remember { mutableStateOf(false) }
    Surface(
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(15.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.undefined),
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(40.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Spacer(modifier = Modifier.weight(1f))
                Box{
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp),
                        onClick = {expanded = true}
                    ){
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            modifier = Modifier
                                .padding(5.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        containerColor = Color.White,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(5.dp)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Iniciar sesion") },
                            onClick = { /*Todo*/ },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Cuenta"
                                )
                            }
                        )
                    }
                }
            }
            Surface(
                shape = RoundedCornerShape(10.dp),
                onClick = { /*TODO*/ },
                shadowElevation = 1.dp,
                color = Color(246, 247, 248, 255),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Image(
                        painterResource(R.drawable.search_icon),
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
                        text = "Publicas",
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
                        text = "Privadas",
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.White,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(songs) { song ->
                        //Songs
                    }
                }
            }
        }
    }
}


