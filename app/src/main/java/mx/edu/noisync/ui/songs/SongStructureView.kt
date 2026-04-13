package mx.edu.noisync.ui.songs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.noisync.data.model.SongSection

private val notes = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
private val flats = mapOf("Db" to "C#", "Eb" to "D#", "Fb" to "E", "Gb" to "F#", "Ab" to "G#", "Bb" to "A#", "Cb" to "B")
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
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        sections.forEach { section ->
            Surface(
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 2.dp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Etiqueta de la sección
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFDFF5E1)
                    ) {
                        Text(
                            text = section.title.uppercase(),
                            color = Color(0xFF0F7A38),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SongStructureLine(
    line: String,
    tonic: String,
    scale: String?,
    transposition: Int
) {
    val items = buildChordWordList(line, tonic, scale, transposition)
    if (items.isEmpty()) return

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                // Acorde: Si no hay, no ocupa espacio vertical pero mantiene alineación
                Text(
                    text = item.chord,
                    color = Color(0xFF198754),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp
                )
                // Palabra: Fuente limpia y legible
                Text(
                    text = item.word,
                    color = Color.Black,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

private data class ChordWord(
    val chord: String,
    val word: String
)

private fun buildChordWordList(
    line: String,
    tonic: String,
    scale: String?,
    transposition: Int
): List<ChordWord> {
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
    if (lastIndex < line.length) tokens.add(line.substring(lastIndex))

    val result = mutableListOf<ChordWord>()
    var currentChord = ""

    tokens.forEach { token ->
        if (token.matches(Regex("^\\$\\d$"))) {
            // Es un acorde: lo guardamos para la siguiente palabra
            val degree = token.drop(1).toIntOrNull()
            currentChord = resolveChord(degree, tonic, scale, transposition)
        } else {
            // Es texto: lo limpiamos de espacios extra y lo dividimos en palabras
            val words = token.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }

            if (words.isNotEmpty()) {
                words.forEachIndexed { i, word ->
                    if (i == 0) {
                        // La primera palabra recibe el acorde acumulado
                        result.add(ChordWord(currentChord, word))
                        currentChord = ""
                    } else {
                        // Las palabras siguientes de este bloque van sin acorde
                        result.add(ChordWord("", word))
                    }
                }
            }
        }
    }

    // Si quedó un acorde al final sin palabra (raro, pero posible)
    if (currentChord.isNotEmpty()) {
        result.add(ChordWord(currentChord, ""))
    }

    return result
}

fun transposeKey(tonic: String, transposition: Int): String {
    val normalizedTonic = flats[tonic] ?: tonic
    val tonicIndex = notes.indexOf(normalizedTonic)
    if (tonicIndex == -1) return tonic
    val index = ((tonicIndex + transposition) % 12 + 12) % 12
    return notes[index]
}

private fun resolveChord(degree: Int?, tonic: String, scale: String?, transposition: Int): String {
    if (degree == null || degree !in 1..7) return ""
    val normalizedTonic = flats[tonic] ?: tonic
    val tonicIndex = notes.indexOf(normalizedTonic)
    if (tonicIndex == -1) return ""
    val useMinorScale = scale == "Menor"
    val degrees = if (useMinorScale) minorDegrees else majorDegrees
    val types = if (useMinorScale) minorTypes else majorTypes
    val index = ((tonicIndex + degrees[degree - 1] + transposition) % 12 + 12) % 12
    return notes[index] + types[degree - 1]
}