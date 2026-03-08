package mx.edu.noisync.data.fake

import mx.edu.noisync.model.Musician

object FakeMusicians {
    val allMusicians = listOf(
        Musician("1", "JD", "Juan Delgado", "Guitarra Eléctrica", "Líder", "Activo", 0xFF8B5CF6),
        Musician("2", "MG", "María González", "Voz Principal", "Músico", "Activo", 0xFFEC4899),
        Musician("3", "CR", "Carlos Ruiz", "Batería", "Músico", "Activo", 0xFFF59E0B),
        Musician("4", "AM", "Ana Martínez", "Bajo", "Músico", "Pendiente", 0xFF10B981),
        Musician("5", "PS", "Pedro Sánchez", "Teclado", "Músico", "Activo", 0xFF3B82F6),
        Musician("6", "LT", "Laura Torres", "Coros", "Músico", "Activo", 0xFF8B5CF6)
    )
}