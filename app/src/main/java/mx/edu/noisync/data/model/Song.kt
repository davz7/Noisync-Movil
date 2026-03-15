package mx.edu.noisync.data.model

enum class SongVisibility {
    PUBLIC,
    PRIVATE
}

data class SongListItem(
    val id: String,
    val bandId: Long,
    val title: String,
    val artistName: String,
    val bpm: Int,
    val originalKey: String,
    val baseScale: String? = null,
    val visibility: SongVisibility,
    val status: String? = null,
    val coverUrl: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val bandName: String? = null
) {
    val isPublic: Boolean
        get() = visibility == SongVisibility.PUBLIC
}

data class SongDetail(
    val id: String,
    val bandId: Long,
    val title: String,
    val artistName: String,
    val bpm: Int,
    val originalKey: String,
    val baseScale: String? = null,
    val visibility: SongVisibility,
    val status: String? = null,
    val sections: List<SongSection>,
    val coverUrl: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val bandName: String? = null
) {
    val isPublic: Boolean
        get() = visibility == SongVisibility.PUBLIC
}

data class SongSection(
    val id: String,
    val order: Int,
    val title: String,
    val lines: List<String>
)
