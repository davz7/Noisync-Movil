package mx.edu.noisync.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun SongCover(
    title: String,
    coverUrl: String?,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4),
    initialsSize: TextUnit
) {
    val normalizedUrl = coverUrl?.trim()?.takeIf { it.isNotEmpty() }

    if (normalizedUrl != null) {
        val painter = rememberAsyncImagePainter(model = normalizedUrl)

        Box(
            modifier = modifier
                .clip(shape)
                .background(songAvatarColor(title)),
            contentAlignment = Alignment.Center
        ) {
            if (painter.state is AsyncImagePainter.State.Success) {
                Image(
                    painter = painter,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                SongCoverFallback(
                    title = title,
                    modifier = Modifier.fillMaxSize(),
                    shape = shape,
                    initialsSize = initialsSize
                )
            }
        }
    } else {
        SongCoverFallback(
            title = title,
            modifier = modifier,
            shape = shape,
            initialsSize = initialsSize
        )
    }
}
