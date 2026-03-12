package mx.edu.noisync.data.fake

import mx.edu.noisync.model.SongDetail
import mx.edu.noisync.model.SongListItem
import mx.edu.noisync.model.SongSection
import mx.edu.noisync.model.SongVisibility

object FakeSongs {
    val publicSongs = listOf(
        SongListItem(
            id = "1",
            bandId = 1,
            title = "Master Of Puppets - Remastered",
            artistName = "Metallica",
            bpm = 120,
            originalKey = "G",
            baseScale = "G",
            visibility = SongVisibility.PUBLIC,
            status = "ACTIVO",
            createdAt = "2026-02-10T10:20:30Z",
            updatedAt = "2026-02-10T10:20:30Z"
        ),
        SongListItem(
            id = "2",
            bandId = 2,
            title = "Nobody - from Kaiju No. 8",
            artistName = "OneRepublic",
            bpm = 114,
            originalKey = "C",
            baseScale = "C",
            visibility = SongVisibility.PUBLIC,
            status = "ACTIVO",
            createdAt = "2026-02-12T10:20:30Z",
            updatedAt = "2026-02-12T10:20:30Z"
        ),
        SongListItem(
            id = "3",
            bandId = 3,
            title = "Abyss - from Kaiju No. 8",
            artistName = "YUNGBLUD",
            bpm = 132,
            originalKey = "D",
            baseScale = "D",
            visibility = SongVisibility.PUBLIC,
            status = "ACTIVO",
            createdAt = "2026-02-15T10:20:30Z",
            updatedAt = "2026-02-15T10:20:30Z"
        ),
        SongListItem(
            id = "4",
            bandId = 4,
            title = "Walk This Way",
            artistName = "Aerosmith",
            bpm = 109,
            originalKey = "E",
            baseScale = "E",
            visibility = SongVisibility.PUBLIC,
            status = "ACTIVO",
            createdAt = "2026-02-18T10:20:30Z",
            updatedAt = "2026-02-18T10:20:30Z"
        )
    )

    val privateBandSongs = listOf(
        SongListItem(
            id = "11",
            bandId = 45,
            title = "Ensayo de apertura",
            artistName = "Los Nocturnos",
            bpm = 98,
            originalKey = "D",
            baseScale = "D",
            visibility = SongVisibility.PRIVATE,
            status = "ACTIVO",
            bandName = "Los Nocturnos",
            createdAt = "2026-03-01T10:20:30Z",
            updatedAt = "2026-03-05T12:00:00Z"
        ),
        SongListItem(
            id = "12",
            bandId = 45,
            title = "Balada de medianoche",
            artistName = "Los Nocturnos",
            bpm = 76,
            originalKey = "Bb",
            baseScale = "Bb",
            visibility = SongVisibility.PRIVATE,
            status = "ACTIVO",
            bandName = "Los Nocturnos",
            createdAt = "2026-03-02T10:20:30Z",
            updatedAt = "2026-03-05T12:00:00Z"
        )
    )

    val accessibleSongs = publicSongs + privateBandSongs
    private val detailsBySongId = mapOf(
        "1" to SongDetail(
            id = "1",
            bandId = 1,
            title = "Master Of Puppets - Remastered",
            artistName = "Metallica",
            bpm = 120,
            originalKey = "G",
            baseScale = "G",
            visibility = SongVisibility.PUBLIC,
            status = "ACTIVO",
            sections = listOf(
                SongSection(
                    id = "1",
                    order = 1,
                    title = "Verso",
                    lines = listOf(
                        "End of passion play, crumbling away",
                        "I'm your source of self-destruction"
                    )
                ),
                SongSection(
                    id = "2",
                    order = 2,
                    title = "Coro",
                    lines = listOf(
                        "Come crawling faster",
                        "Obey your master"
                    )
                )
            )
        ),
        "11" to SongDetail(
            id = "11",
            bandId = 45,
            title = "Ensayo de apertura",
            artistName = "Los Nocturnos",
            bpm = 98,
            originalKey = "D",
            baseScale = "D",
            visibility = SongVisibility.PRIVATE,
            status = "ACTIVO",
            bandName = "Los Nocturnos",
            sections = listOf(
                SongSection(
                    id = "11-1",
                    order = 1,
                    title = "Verso 1",
                    lines = listOf(
                        "Luces bajas, cuenta atras, entra la voz",
                        "Marca el golpe y cae justo en el compas"
                    )
                ),
                SongSection(
                    id = "11-2",
                    order = 2,
                    title = "Coro",
                    lines = listOf(
                        "Sigue el pulso hasta el final",
                        "No sueltes el aire al cantar"
                    )
                )
            ),
            createdAt = "2026-03-01T10:20:30Z",
            updatedAt = "2026-03-05T12:00:00Z"
        ),
        "12" to SongDetail(
            id = "12",
            bandId = 45,
            title = "Balada de medianoche",
            artistName = "Los Nocturnos",
            bpm = 76,
            originalKey = "Bb",
            baseScale = "Bb",
            visibility = SongVisibility.PRIVATE,
            status = "ACTIVO",
            bandName = "Los Nocturnos",
            sections = listOf(
                SongSection(
                    id = "12-1",
                    order = 1,
                    title = "Verso",
                    lines = listOf(
                        "Todo cae lento cuando empieza el son",
                        "Y la voz se queda sola en la habitacion"
                    )
                ),
                SongSection(
                    id = "12-2",
                    order = 2,
                    title = "Puente",
                    lines = listOf(
                        "Sube una vez mas y vuelve al compas",
                        "Deja que la noche hable por detras"
                    )
                )
            ),
            createdAt = "2026-03-02T10:20:30Z",
            updatedAt = "2026-03-05T12:00:00Z"
        )
    )

    private val listItemsById = accessibleSongs.associateBy(SongListItem::id)

    fun getSongDetail(songId: String?): SongDetail {
        val listItem = findSongById(songId)

        return detailsBySongId[songId]
            ?: SongDetail(
                id = listItem?.id ?: "missing",
                bandId = listItem?.bandId ?: -1,
                title = listItem?.title ?: "Cancion no disponible",
                artistName = listItem?.artistName ?: "Artista no disponible",
                bpm = listItem?.bpm ?: 0,
                originalKey = listItem?.originalKey ?: "--",
                baseScale = listItem?.baseScale,
                visibility = listItem?.visibility ?: SongVisibility.PUBLIC,
                status = listItem?.status,
                bandName = listItem?.bandName,
                coverUrl = listItem?.coverUrl,
                createdAt = listItem?.createdAt,
                updatedAt = listItem?.updatedAt,
                sections = listOf(
                    SongSection(
                        id = "missing",
                        order = 1,
                        title = "Contenido",
                        lines = listOf("La letra de esta cancion estara disponible cuando conectemos el backend.")
                    )
                )
            )
    }

    fun findSongById(songId: String?): SongListItem? = songId?.let(listItemsById::get)
}
