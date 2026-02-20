package mx.edu.noisync.data.fake

import mx.edu.noisync.model.Song

object FakeSongs{
    val publicSongs = listOf(
        Song(id = "1", title = "Master Of Puppets - Remastered", bandName = "Metallica", isPublic = true),
        Song(id = "2", title = "Nobody - from Kaiju No. 8", bandName = "OneRepublic", isPublic = true),
        Song(id = "3", title = "Abyss - from Kaiju No. 8", bandName = "YUNGBLUD", isPublic = true),
        Song(id = "4", title = "Walk This Way", bandName = "Aerosmith", isPublic = true),
        Song(id = "5", title = "Still D.R.E.", bandName = "Dr. Dre, Snoop Dogg", isPublic = true),
        Song(id = "6", title = "Houdini", bandName = "Eminem", isPublic = true),
        Song(id = "7", title = "...And to Those I Love, Thanks for Sticking Around", bandName = "Suicideboy", isPublic = true),
        Song(id = "8", title = "All Star", bandName = "Smash Mouth", isPublic = true),
        Song(id = "9", title = "I Wanna Be Your Dog", bandName = "Joan Jett & the Blackhearts", isPublic = true),
        Song(id = "10", title = "Snakes (from the series Arcane)", bandName = "MIYAVI, PVRIS, Arcane, League of Legends", isPublic = true),
    )
}