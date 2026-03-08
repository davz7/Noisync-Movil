package mx.edu.noisync.data.fake

import mx.edu.noisync.model.Instrument

object FakeInstruments {
    val allInstruments = listOf(
        Instrument("1", "Guitarra Eléctrica", 3, 0xFF8B5CF6),
        Instrument("2", "Bajo", 2, 0xFFEC4899),
        Instrument("3", "Batería", 1, 0xFFF59E0B),
        Instrument("4", "Teclado", 2, 0xFF10B981),
        Instrument("5", "Voz Principal", 1, 0xFF3B82F6),
        Instrument("6", "Coros", 4, 0xFF8B5CF6),
        Instrument("7", "Saxofón", 1, 0xFFEC4899)
    )
}