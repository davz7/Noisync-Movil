package mx.edu.noisync.data.model

data class LoginRequest(
    val identifier: String,
    val password: String
)

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
    val bandId: Long,
    val role: String,
    val mustChangePassword: Boolean
)

enum class UserRole {
    VISITOR,
    MUSICIAN,
    LEADER
}

data class User(
    val id: String,
    val email: String,
    val name: String,
    val role: UserRole,
    val bandId: String?
)
