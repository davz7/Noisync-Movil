package mx.edu.noisync.ui.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.noisync.data.model.Musician
import mx.edu.noisync.data.repository.RepositoryProvider
import mx.edu.noisync.data.repository.RepositoryResult

sealed class TeamUiState {
    object Loading : TeamUiState()
    data class Success(val musicians: List<Musician>) : TeamUiState()
    data class Error(val message: String) : TeamUiState()
}

class TeamViewModel : ViewModel() {
    private val musicianRepository = RepositoryProvider.musicianRepository

    private val _uiState = MutableStateFlow<TeamUiState>(TeamUiState.Loading)
    val uiState: StateFlow<TeamUiState> = _uiState.asStateFlow()

    init {
        loadMusicians()
    }

    fun loadMusicians() {
        viewModelScope.launch {
            _uiState.value = TeamUiState.Loading
            _uiState.value = when (val result = musicianRepository.getMusicians()) {
                is RepositoryResult.Success -> TeamUiState.Success(result.data)
                is RepositoryResult.Error -> TeamUiState.Error(
                    when (result.code) {
                        401 -> "Tu sesion ya no es valida. Inicia sesion otra vez."
                        403 -> "No tienes permisos para consultar musicos."
                        else -> result.message
                    }
                )
            }
        }
    }
}
