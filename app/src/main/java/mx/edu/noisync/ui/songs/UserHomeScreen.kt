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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.noisync.R
import mx.edu.noisync.data.model.SongListItem

@Composable
fun UserHomeScreen(
    songs: List<SongListItem>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedFilter: UserSongsFilter,
    onShowAll: () -> Unit,
    onShowPublic: () -> Unit,
    onShowPrivate: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onRetry: (() -> Unit)? = null,
    onOpenSong: (SongListItem) -> Unit?,
    onOpenTeam: () -> Unit,
    onOpenInstruments: () -> Unit,
    onOpenProfile: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Profile Photo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Spacer(modifier = Modifier.weight(1f))
                Box {
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp),
                        onClick = { expanded = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.Black,
                            modifier = Modifier.padding(5.dp)
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
                            text = { Text("Musicos") },
                            onClick = {
                                expanded = false
                                onOpenTeam()
                            },
                            colors = MenuDefaults.itemColors(textColor = Color.Black)
                        )
                        DropdownMenuItem(
                            text = { Text("Instrumentos") },
                            onClick = {
                                expanded = false
                                onOpenInstruments()
                            },
                            colors = MenuDefaults.itemColors(textColor = Color.Black)
                        )
                        DropdownMenuItem(
                            text = { Text("Mi perfil") },
                            onClick = {
                                expanded = false
                                onOpenProfile()
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = Color.Black,
                                leadingIconColor = Color.Black
                            ),
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
                shadowElevation = 1.dp,
                color = Color(246, 247, 248, 255),
                modifier = Modifier.fillMaxWidth()
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
                modifier = Modifier.padding(5.dp)
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
                    onClick = onShowPublic,
                    shape = RoundedCornerShape(10.dp),
                    shadowElevation = 1.dp,
                    color = if (selectedFilter == UserSongsFilter.PUBLIC) Color(0xFFE9ECEF) else Color(0xFFF4F5F6)
                ) {
                    Text(
                        text = "Publicas",
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Surface(
                    onClick = onShowPrivate,
                    shape = RoundedCornerShape(10.dp),
                    shadowElevation = 1.dp,
                    color = if (selectedFilter == UserSongsFilter.PRIVATE) Color(0xFFE9ECEF) else Color(0xFFF4F5F6)
                ) {
                    Text(
                        text = "Privadas",
                        modifier = Modifier.padding(10.dp)
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
        }
    }
}
