package mx.edu.noisync.data.remote.dto

import com.google.gson.annotations.SerializedName
import mx.edu.noisync.data.model.Instrument
import mx.edu.noisync.data.model.Musician

data class MusicianResponseDto(
    @SerializedName("userId") val userId: Long,
    @SerializedName("bandId") val bandId: Long? = null,
    @SerializedName("nombreCompleto") val nombreCompleto: String,
    @SerializedName("correo") val correo: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("estatus") val estatus: String? = null,
    @SerializedName("instrumentos") val instrumentos: List<String> = emptyList()
)

data class InstrumentResponseDto(
    @SerializedName("instrumentId") val instrumentId: Long,
    @SerializedName("bandId") val bandId: Long? = null,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("activo") val activo: Int? = null
)

fun MusicianResponseDto.toDomain(): Musician {
    return Musician(
        id = userId.toString(),
        bandId = bandId,
        fullName = nombreCompleto,
        email = correo,
        username = username,
        status = estatus,
        instruments = instrumentos
    )
}

fun InstrumentResponseDto.toDomain(musiciansCount: Int = 0): Instrument {
    return Instrument(
        id = instrumentId.toString(),
        bandId = bandId,
        name = nombre,
        isActive = activo == 1,
        musiciansCount = musiciansCount
    )
}
