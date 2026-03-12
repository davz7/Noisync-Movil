package mx.edu.noisync.data.model

data class LoginRequest(
    val identifier: String,
    val password: String
)

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String,
    val confirmPassword: String
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
    val bandId: Long,
    val role: String,
    val mustChangePassword: Boolean
)

data class ChangePasswordResponse(
    val ok: Boolean,
    val message: String
)

data class UserProfile(
    val userId: Long,
    val bandId: Long?,
    val role: String,
    val username: String?,
    val fullName: String?,
    val email: String?,
    val phone: String?,
    val status: String?,
    val firstLogin: Int?,
    val active: Int?,
    val bandName: String?,
    val bandDescription: String?
)
