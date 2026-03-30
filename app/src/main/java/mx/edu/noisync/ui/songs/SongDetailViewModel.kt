package mx.edu.noisync.ui.songs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.noisync.data.model.SongDetail
import mx.edu.noisync.data.repository.RepositoryProvider
import mx.edu.noisync.data.repository.RepositoryResult

sealed class SongDetailUiState {
    object Idle : SongDetailUiState()
    object Loading : SongDetailUiState()
    data class Success(val song: SongDetail) : SongDetailUiState()
    data class Error(val message: String) : SongDetailUiState()
}

class SongDetailViewModel : ViewModel() {
    private val songRepository = RepositoryProvider.songRepository

    private val _uiState = MutableStateFlow<SongDetailUiState>(SongDetailUiState.Idle)
    val uiState: StateFlow<SongDetailUiState> = _uiState.asStateFlow()

    fun loadSong(songId: String?, isPublicSong: Boolean) {
        if (songId.isNullOrBlank()) {
            _uiState.value = SongDetailUiState.Error("No se encontro la cancion solicitada.")
            return
        }

        viewModelScope.launch {
            _uiState.value = SongDetailUiState.Loading
            val result = if (isPublicSong) {
                songRepository.getPublicSongDetail(songId)
            } else {
                val privateResult = songRepository.getSongDetail(songId)
                if (privateResult is RepositoryResult.Error && privateResult.code in setOf(401, 403, 404)) {
                    songRepository.getPublicSongDetail(songId)
                } else {
                    privateResult
                }
            }

            _uiState.value = when (result) {
                is RepositoryResult.Success -> SongDetailUiState.Success(result.data)
                is RepositoryResult.Error -> SongDetailUiState.Error(
                    when (result.code) {
                        401 -> "Tu sesion ya no es valida. Inicia sesion otra vez."
                        404 -> "La cancion ya no esta disponible."
                        else -> result.message
                    }
                )
            }
        }
    }
}
