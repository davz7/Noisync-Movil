package mx.edu.noisync.data.model

data class Instrument(
    val id: String,
    val bandId: Long?,
    val name: String,
    val isActive: Boolean,
    val musiciansCount: Int = 0
)
