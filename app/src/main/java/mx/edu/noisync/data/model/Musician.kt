package mx.edu.noisync.data.model

data class Musician(
    val id: String,
    val bandId: Long?,
    val fullName: String,
    val email: String?,
    val username: String?,
    val status: String?,
    val instruments: List<String>
)
