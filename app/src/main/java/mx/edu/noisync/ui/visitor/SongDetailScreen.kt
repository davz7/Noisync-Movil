package mx.edu.noisync.ui.visitor

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.noisync.R
import mx.edu.noisync.model.Song

@Composable
fun SongDetailScreen(navController: NavController){
    Surface(
        color = Color.White,
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(15.dp)
        ){
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.White,
                onClick = { navController.popBackStack() }
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = "Volver",
                        modifier = Modifier
                            .padding(8.dp)

                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(10.dp),
                shadowElevation = 1.dp,
                color = Color(0xFFF4F5F6),
                modifier = Modifier
            ){
                Column(
                    verticalArrangement = Arrangement.Center,
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
                            painter = painterResource(R.drawable.undefined),
                            contentDescription = "Song Image",
                            modifier = Modifier
                                .size(70.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                        ){
                            Text(
                                text = "Song name"
                            )
                            Text(
                                text = "Band name"
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = "Tono: G"
                                )
                                Spacer(
                                    modifier = Modifier
                                        .padding(15.dp)
                                )
                                Text(
                                    text = "BPM: 120"
                                )
                            }

                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Transpocicion"
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                shadowElevation = 1.dp,
                                color = Color(0xFFF4F5F6),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                            ){
                                Image(
                                    painterResource(R.drawable.boton1),
                                    contentDescription = "boton1",
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clickable{/* Todo */}
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                shadowElevation = 1.dp,
                                color = Color(0xFFF4F5F6),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                            ){
                                Image(
                                    painterResource(R.drawable.boton2),
                                    contentDescription = "boton2",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable{/* Todo */}
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                shadowElevation = 1.dp,
                                color = Color(0xFFF4F5F6),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                            ){
                                Image(
                                    painterResource(R.drawable.boton3),
                                    contentDescription = "boton3",
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clickable{/* Todo */}
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                shadowElevation = 1.dp,
                                color = Color(0xFFF4F5F6),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                            ){
                                Image(
                                    painterResource(R.drawable.boton4),
                                    contentDescription = "boton4",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable{/* Todo */}
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                shadowElevation = 1.dp,
                                color = Color(0xFFF4F5F6),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                            ){
                                Image(
                                    painterResource(R.drawable.boton5),
                                    contentDescription = "boton5",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .clickable{/* Todo */}
                                )
                            }
                        }
                    }
                }
            }
            Surface(
                shape = RoundedCornerShape(10.dp),
                shadowElevation = 1.dp,
                color = Color(246, 247, 248, 255),
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    //Aqui van las Notas de la cancion

                }
            }
        }
    }
}