package mx.edu.noisync.ui.instruments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.noisync.data.model.Instrument
import mx.edu.noisync.data.repository.RepositoryProvider
import mx.edu.noisync.data.repository.RepositoryResult

sealed class InstrumentsUiState {
    object Loading : InstrumentsUiState()
    data class Success(val instruments: List<Instrument>) : InstrumentsUiState()
    data class Error(val message: String) : InstrumentsUiState()
}

class InstrumentsViewModel : ViewModel() {
    private val instrumentRepository = RepositoryProvider.instrumentRepository
    private val musicianRepository = RepositoryProvider.musicianRepository

    private val _uiState = MutableStateFlow<InstrumentsUiState>(InstrumentsUiState.Loading)
    val uiState: StateFlow<InstrumentsUiState> = _uiState.asStateFlow()

    init {
        loadInstruments()
    }

    fun loadInstruments() {
        viewModelScope.launch {
            _uiState.value = InstrumentsUiState.Loading

            val instrumentsResult = instrumentRepository.getInstruments()
            if (instrumentsResult is RepositoryResult.Error) {
                _uiState.value = InstrumentsUiState.Error(mapErrorMessage(instrumentsResult.code, instrumentsResult.message))
                return@launch
            }

            val musiciansResult = musicianRepository.getMusicians()
            if (musiciansResult is RepositoryResult.Error) {
                _uiState.value = InstrumentsUiState.Error(mapErrorMessage(musiciansResult.code, musiciansResult.message))
                return@launch
            }

            val instruments = (instrumentsResult as RepositoryResult.Success).data
            val musicians = (musiciansResult as RepositoryResult.Success).data

            val instrumentsWithCount = instruments.map { instrument ->
                instrument.copy(
                    musiciansCount = musicians.count { musician ->
                        musician.instruments.any { it.equals(instrument.name, ignoreCase = true) }
                    }
                )
            }

            _uiState.value = InstrumentsUiState.Success(instrumentsWithCount)
        }
    }

    private fun mapErrorMessage(code: Int?, fallback: String): String {
        return when (code) {
            401 -> "Tu sesion ya no es valida. Inicia sesion otra vez."
            403 -> "No tienes permisos para consultar instrumentos."
            else -> fallback
        }
    }
}
