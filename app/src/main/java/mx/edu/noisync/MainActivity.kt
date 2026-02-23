package mx.edu.noisync

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.edu.noisync.data.fake.FakeSongs
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
