package mx.edu.noisync.ui.songs

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mx.edu.noisync.data.model.SongSection
import kotlin.math.abs
import kotlin.math.max

private val notes = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
private val flats = mapOf(
    "Db" to "C#",
    "Eb" to "D#",
    "Fb" to "E",
    "Gb" to "F#",
    "Ab" to "G#",
    "Bb" to "A#",
    "Cb" to "B"
)
private val majorDegrees = listOf(0, 2, 4, 5, 7, 9, 11)
private val minorDegrees = listOf(0, 2, 3, 5, 7, 8, 10)
private val majorTypes = listOf("", "m", "m", "", "", "m", "dim")
private val minorTypes = listOf("m", "dim", "", "m", "m", "", "")

@Composable
fun SongStructureView(
    sections: List<SongSection>,
    tonic: String,
    scale: String?,
    transposition: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        sections.forEach { section ->
            Surface(
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 1.dp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFDFF5E1)
                    ) {
                        Text(
                            text = section.title,
                            color = Color(0xFF0F7A38),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }

                    section.lines.forEach { line ->
                        SongStructureLine(
                            line = line,
                            tonic = tonic,
                            scale = scale,
                            transposition = transposition
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SongStructureLine(
    line: String,
    tonic: String,
    scale: String?,
    transposition: Int
) {
    val lineRender = buildLineRender(
        line = line,
        tonic = tonic,
        scale = scale,
        transposition = transposition
    ) ?: return

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.horizontalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = lineRender.chords,
            color = Color(0xFF198754),
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Monospace,
            softWrap = false,
            overflow = TextOverflow.Clip
        )
        Text(
            text = lineRender.lyrics,
            color = Color.Black,
            fontFamily = FontFamily.Monospace,
            softWrap = false,
            overflow = TextOverflow.Clip
        )
    }
}

private data class LineRender(
    val chords: String,
    val lyrics: String
)

private fun buildLineRender(
    line: String,
    tonic: String,
    scale: String?,
    transposition: Int
): LineRender? {
    val tokenRegex = Regex("(\\$\\d)")
    val tokens = mutableListOf<String>()
    var lastIndex = 0

    tokenRegex.findAll(line).forEach { match ->
        if (match.range.first > lastIndex) {
            tokens.add(line.substring(lastIndex, match.range.first))
        }
        tokens.add(match.value)
        lastIndex = match.range.last + 1
    }

    if (lastIndex < line.length) {
        tokens.add(line.substring(lastIndex))
    }

    if (tokens.isEmpty()) {
        tokens.add(line)
    }

    val pairs = mutableListOf<Pair<String, String>>()
    var index = 0

    while (index < tokens.size) {
        val token = tokens[index]
        if (token.matches(Regex("^\\$\\d$"))) {
            val degree = token.drop(1).toIntOrNull()
            val chord = resolveChord(degree, tonic, scale, transposition)
            val text = tokens.getOrNull(index + 1).orEmpty()
            pairs.add(chord to text)
            index += 2
        } else {
            if (token.isNotEmpty()) {
                pairs.add("" to token)
            }
            index += 1
        }
    }

    if (pairs.isEmpty()) {
        return null
    }

    val chords = buildString {
        pairs.forEach { (chord, text) ->
            append(chord.padEnd(max(text.length, chord.length + 1), ' '))
        }
    }

    val lyrics = buildString {
        pairs.forEach { (chord, text) ->
            append(text.padEnd(max(text.length, chord.length + 1), ' '))
        }
    }

    return LineRender(chords = chords, lyrics = lyrics)
}

fun transposeKey(tonic: String, transposition: Int): String {
    val normalizedTonic = flats[tonic] ?: tonic
    val tonicIndex = notes.indexOf(normalizedTonic)
    if (tonicIndex == -1) {
        return tonic
    }

    val index = ((tonicIndex + transposition) % 12 + 12) % 12
    return notes[index]
}

private fun resolveChord(
    degree: Int?,
    tonic: String,
    scale: String?,
    transposition: Int
): String {
    if (degree == null || degree !in 1..7) {
        return "$${degree ?: ""}"
    }

    val normalizedTonic = flats[tonic] ?: tonic
    val tonicIndex = notes.indexOf(normalizedTonic)
    if (tonicIndex == -1) {
        return "$$degree"
    }

    val useMinorScale = scale == "Menor"
    val degrees = if (useMinorScale) minorDegrees else majorDegrees
    val types = if (useMinorScale) minorTypes else majorTypes
    val index = ((tonicIndex + degrees[degree - 1] + transposition) % 12 + 12) % 12
    return notes[index] + types[degree - 1]
}
