package mx.edu.noisync.ui.songs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import okhttp3.Headers
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

private const val COVER_DECODE_PX = 512

private const val COVER_USER_AGENT =
    "Mozilla/5.0 (Linux; Android 13; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"

@Composable
fun SongCover(
    title: String,
    coverUrl: String?,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4),
    initialsSize: TextUnit
) {
    val normalizedUrl = coverUrl?.trim()?.takeIf { it.isNotEmpty() }

    if (normalizedUrl == null) {
        SongCoverFallback(
            title = title,
            modifier = modifier,
            shape = shape,
            initialsSize = initialsSize
        )
        return
    }

    val context = LocalContext.current
    val request = remember(normalizedUrl) {
        ImageRequest.Builder(context)
            .data(normalizedUrl)
            .size(COVER_DECODE_PX, COVER_DECODE_PX)
            .crossfade(200)
            .headers(Headers.headersOf("User-Agent", COVER_USER_AGENT))
            .build()
    }
    val painter = rememberAsyncImagePainter(model = request)

    Box(
        modifier = modifier
            .clip(shape)
            .background(songAvatarColor(title)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        if (painter.state !is AsyncImagePainter.State.Success) {
            SongCoverFallback(
                title = title,
                modifier = Modifier.fillMaxSize(),
                shape = shape,
                initialsSize = initialsSize
            )
        }
    }
}
