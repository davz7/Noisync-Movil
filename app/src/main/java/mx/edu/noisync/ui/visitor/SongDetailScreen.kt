package mx.edu.noisync.ui.visitor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.edu.noisync.R

@Composable
fun SongDetailScreen(){
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
            Text(
                text = "<  Volver",
                modifier = Modifier
                    .padding(8.dp)

            )

        }
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    //Botones para subir o bajar de tono...
                }
            }
        }

    }
}

@Preview(showBackground = true, name = "SongDetailPreview")
@Composable
fun SongDetailPreview(){
    SongDetailScreen()
}