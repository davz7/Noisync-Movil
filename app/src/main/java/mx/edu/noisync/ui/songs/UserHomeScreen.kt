package mx.edu.noisync.ui.songs

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.noisync.R
import mx.edu.noisync.data.model.SongListItem
import mx.edu.noisync.ui.components.AuthenticatedBottomBar
import mx.edu.noisync.ui.components.AuthenticatedDestination

@Composable
fun UserHomeScreen(
    songs: List<SongListItem>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedFilter: UserSongsFilter,
    onShowAll: () -> Unit,
    onShowRecent: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onRetry: (() -> Unit)? = null,
    onOpenSong: (SongListItem) -> Unit?,
    onOpenSongs: () -> Unit,
    onOpenTeam: () -> Unit,
    onOpenInstruments: () -> Unit,
    onOpenProfile: () -> Unit
) {
    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 15.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Canciones",
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }
            Surface(
                shape = RoundedCornerShape(10.dp),
                shadowElevation = 1.dp,
                color = Color(246, 247, 248, 255),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.search_icon),
                                contentDescription = "Busqueda",
                                modifier = Modifier.size(20.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 5.dp)
                            ) {
                                if (searchQuery.isBlank()) {
                                    Text(
                                        text = "Buscar por titulo o artista",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                                innerTextField()
                            }
                        }
                    }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
            ) {
                Surface(
                    onClick = onShowAll,
                    shape = RoundedCornerShape(10.dp),
                    shadowElevation = 1.dp,
                    color = if (selectedFilter == UserSongsFilter.ALL) Color(0xFFE9ECEF) else Color(0xFFF4F5F6)
                ) {
                    Text(
                        text = "Todas",
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Surface(
                    onClick = onShowRecent,
                    shape = RoundedCornerShape(10.dp),
                    shadowElevation = 1.dp,
                    color = if (selectedFilter == UserSongsFilter.RECENT) Color(0xFFE9ECEF) else Color(0xFFF4F5F6)
                ) {
                    Text(
                        text = "Recientes",
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 10.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        isLoading -> {
                            CircularProgressIndicator(color = Color.Black)
                        }

                        errorMessage != null -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = errorMessage,
                                    color = Color.Gray
                                )
                                if (onRetry != null) {
                                    Surface(
                                        onClick = onRetry,
                                        shape = RoundedCornerShape(10.dp),
                                        shadowElevation = 1.dp,
                                        color = Color.Black
                                    ) {
                                        Text(
                                            text = "Reintentar",
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                                        )
                                    }
                                }
                            }
                        }

                        songs.isEmpty() -> {
                            Text(
                                text = "No hay canciones disponibles.",
                                color = Color.Gray
                            )
                        }

                        else -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(songs) { song ->
                                    SongCard(song = song, onOpen = { onOpenSong(song) })
                                }
                            }
                        }
                    }
                }
            }
            AuthenticatedBottomBar(
                selectedDestination = AuthenticatedDestination.SONGS,
                onOpenSongs = onOpenSongs,
                onOpenTeam = onOpenTeam,
                onOpenInstruments = onOpenInstruments,
                onOpenProfile = onOpenProfile
            )
        }
    }
}
