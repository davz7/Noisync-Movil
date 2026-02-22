package mx.edu.noisync

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.edu.noisync.data.fake.FakeSongs
import mx.edu.noisync.ui.theme.NoisyncTheme
import mx.edu.noisync.ui.theme.visitor.VisitorHomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VisitorHomeScreen(
                songs = FakeSongs.publicSongs,
                onOpenSong = {
                    song ->
                    Log.d("Song", song.title)
                }
            )
            }
        }
    }
