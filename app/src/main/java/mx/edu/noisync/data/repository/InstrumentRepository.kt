package mx.edu.noisync.data.repository

import mx.edu.noisync.data.model.Instrument

interface InstrumentRepository {
    suspend fun getInstruments(): RepositoryResult<List<Instrument>>
}
