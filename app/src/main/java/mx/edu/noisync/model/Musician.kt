package mx.edu.noisync.model

data class Musician(
    val id: String,
    val initials: String,
    val name: String,
    val instrument: String,
    val role: String,
    val status: String, // "Activo", "Pendiente"
    val avatarColor: Long
)