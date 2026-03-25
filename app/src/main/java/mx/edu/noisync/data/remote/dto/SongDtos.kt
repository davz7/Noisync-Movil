package mx.edu.noisync.data.remote.dto

import com.google.gson.annotations.SerializedName
import mx.edu.noisync.core.network.RetrofitClient
import mx.edu.noisync.data.model.SongDetail
import mx.edu.noisync.data.model.SongListItem
import mx.edu.noisync.data.model.SongSection
import mx.edu.noisync.data.model.SongVisibility

data class PageResponseDto<T>(
    @SerializedName("content") val content: List<T>,
    @SerializedName("page") val page: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("totalElements") val totalElements: Long,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("last") val last: Boolean
)

data class SongResponseDto(
    @SerializedName("songId") val songId: Long,
    @SerializedName("bandId") val bandId: Long,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("artistaAutor") val artistaAutor: String,
    @SerializedName("bpm") val bpm: Int,
    @SerializedName("tonoOriginal") val tonoOriginal: String,
    @SerializedName("escalaBase") val escalaBase: String? = null,
    @SerializedName("visibilidad") val visibilidad: String,
    @SerializedName("estatus") val estatus: String? = null,
    @SerializedName("coverUrl") val coverUrl: String? = null,
    @SerializedName("nombreBanda") val nombreBanda: String? = null,
    @SerializedName("fechaCreacion") val fechaCreacion: String? = null,
    @SerializedName("fechaActualizacion") val fechaActualizacion: String? = null
)

data class SectionResponseDto(
    @SerializedName("sectionId") val sectionId: Long,
    @SerializedName("songId") val songId: Long,
    @SerializedName("ordenSeccion") val ordenSeccion: Int,
    @SerializedName("etiqueta") val etiqueta: String,
    @SerializedName("contenido") val contenido: String
)

fun SongResponseDto.toListItem(): SongListItem {
    return SongListItem(
        id = songId.toString(),
        bandId = bandId,
        title = titulo,
        artistName = artistaAutor,
        bpm = bpm,
        originalKey = tonoOriginal,
        baseScale = escalaBase,
        visibility = visibilidad.toVisibility(),
        status = estatus,
        coverUrl = RetrofitClient.resolveUrl(coverUrl),
        bandName = nombreBanda,
        createdAt = fechaCreacion,
        updatedAt = fechaActualizacion
    )
}

fun SongResponseDto.toDetail(sections: List<SectionResponseDto>): SongDetail {
    return SongDetail(
        id = songId.toString(),
        bandId = bandId,
        title = titulo,
        artistName = artistaAutor,
        bpm = bpm,
        originalKey = tonoOriginal,
        baseScale = escalaBase,
        visibility = visibilidad.toVisibility(),
        status = estatus,
        sections = sections.map { it.toDomain() },
        coverUrl = RetrofitClient.resolveUrl(coverUrl),
        bandName = nombreBanda,
        createdAt = fechaCreacion,
        updatedAt = fechaActualizacion
    )
}

fun SectionResponseDto.toDomain(): SongSection {
    return SongSection(
        id = sectionId.toString(),
        order = ordenSeccion,
        title = etiqueta,
        lines = contenido.split("\n")
    )
}

private fun String.toVisibility(): SongVisibility {
    return when (uppercase()) {
        "PRIVATE" -> SongVisibility.PRIVATE
        else -> SongVisibility.PUBLIC
    }
}
