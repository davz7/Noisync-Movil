package mx.edu.noisync.data.remote.dto

import com.google.gson.annotations.SerializedName
import mx.edu.noisync.data.model.ChangePasswordResponse
import mx.edu.noisync.data.model.UserProfile

data class MeResponseDto(
    @SerializedName("userId") val userId: Long,
    @SerializedName("bandId") val bandId: Long?,
    @SerializedName("role") val role: String,
    @SerializedName("username") val username: String? = null,
    @SerializedName("nombreCompleto") val nombreCompleto: String? = null,
    @SerializedName("correo") val correo: String? = null,
    @SerializedName("telefono") val telefono: String? = null,
    @SerializedName("estatus") val estatus: String? = null,
    @SerializedName("primerLogin") val primerLogin: Int? = null,
    @SerializedName("activo") val activo: Int? = null,
    @SerializedName("bandNombre") val bandNombre: String? = null,
    @SerializedName("bandDescripcion") val bandDescripcion: String? = null
)

data class ChangePasswordResponseDto(
    @SerializedName("message") val message: String? = null,
    @SerializedName("ok") val ok: Boolean? = null
)

fun MeResponseDto.toDomain(): UserProfile {
    return UserProfile(
        userId = userId,
        bandId = bandId,
        role = role,
        username = username,
        fullName = nombreCompleto,
        email = correo,
        phone = telefono,
        status = estatus,
        firstLogin = primerLogin,
        active = activo,
        bandName = bandNombre,
        bandDescription = bandDescripcion
    )
}

fun ChangePasswordResponseDto.toDomain(): ChangePasswordResponse {
    return ChangePasswordResponse(
        ok = ok ?: true,
        message = message ?: "Contrasena actualizada"
    )
}
