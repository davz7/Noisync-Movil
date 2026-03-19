package mx.edu.noisync.ui.songs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import kotlin.math.abs

@Composable
fun SongCoverFallback(
    title: String,
    modifier: Modifier = Modifier,
    shape: Shape,
    initialsSize: TextUnit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(songAvatarColor(title)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = songAvatarInitials(title),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = initialsSize,
            textAlign = TextAlign.Center
        )
    }
}

fun songAvatarInitials(title: String): String {
    val words = title.trim()
        .split(Regex("\\s+"))
        .filter { it.isNotBlank() }

    if (words.isEmpty()) {
        return "NS"
    }

    return if (words.size == 1) {
        words[0].take(2).uppercase()
    } else {
        (words[0].take(1) + words[1].take(1)).uppercase()
    }
}

fun songAvatarColor(title: String): Color {
    var hash = 0

    for (character in title) {
        hash = character.code + ((hash shl 5) - hash)
    }

    val hue = abs(hash % 360)
    return hslColor(hue.toFloat(), 0.60f, 0.55f)
}

private fun hslColor(hue: Float, saturation: Float, lightness: Float): Color {
    val c = (1f - kotlin.math.abs(2f * lightness - 1f)) * saturation
    val x = c * (1f - kotlin.math.abs((hue / 60f) % 2f - 1f))
    val m = lightness - c / 2f

    val (r1, g1, b1) = when {
        hue < 60f -> Triple(c, x, 0f)
        hue < 120f -> Triple(x, c, 0f)
        hue < 180f -> Triple(0f, c, x)
        hue < 240f -> Triple(0f, x, c)
        hue < 300f -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }

    return Color(
        red = r1 + m,
        green = g1 + m,
        blue = b1 + m,
        alpha = 1f
    )
}
