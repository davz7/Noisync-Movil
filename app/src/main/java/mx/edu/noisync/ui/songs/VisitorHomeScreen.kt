package mx.edu.noisync.ui.songs

import android.content.Intent
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.noisync.R
import mx.edu.noisync.data.model.SongListItem
import mx.edu.noisync.ui.auth.LoginActivity

@Composable
fun VisitorHomeScreen(
    songs: List<SongListItem>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedFilter: VisitorSongsFilter,
    onShowAll: () -> Unit,
    onShowRecent: () -> Unit,
    isLoading: Boolean = false,
    isLoadingMore: Boolean = false,
    hasMore: Boolean = false,
    errorMessage: String? = null,
    onRetry: (() -> Unit)? = null,
    onLoadMore: (() -> Unit)? = null,
    onOpenSong: (SongListItem) -> Unit?
) {
    val context = LocalContext.current

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
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Noisync")
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    color = Color(0xFFF4F5F6),
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        context.startActivity(Intent(context, LoginActivity::class.java))
                    }
                ) {
                    Text(
                        text = "Iniciar sesion",
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                    )
                }
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
                                Text(text = errorMessage, color = Color.Gray)
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
                                text = "No hay canciones publicas disponibles.",
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onLoadMore?.invoke() },
                    enabled = hasMore && !isLoadingMore && !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF2F3F4),
                        contentColor = Color.Black,
                        disabledContainerColor = Color(0xFFF2F3F4),
                        disabledContentColor = Color.Gray
                    )
                ) {
                    Text(
                        text = when {
                            isLoadingMore -> "Cargando..."
                            hasMore -> "Cargar mas"
                            else -> "Sin mas canciones"
                        }
                    )
                }
            }
        }
    }
}
