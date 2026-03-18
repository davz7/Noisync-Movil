package mx.edu.noisync.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.noisync.data.model.SongListItem
import mx.edu.noisync.data.repository.RepositoryProvider
import mx.edu.noisync.data.repository.RepositoryResult

sealed class UserSongsUiState {
    object Loading : UserSongsUiState()
    data class Success(val songs: List<SongListItem>) : UserSongsUiState()
    data class Error(val message: String) : UserSongsUiState()
}

class UserSongsViewModel : ViewModel() {
    private val songRepository = RepositoryProvider.songRepository
    private var searchJob: Job? = null

    private val _uiState = MutableStateFlow<UserSongsUiState>(UserSongsUiState.Loading)
    val uiState: StateFlow<UserSongsUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadSongs()
    }

    fun loadSongs(query: String? = null) {
        viewModelScope.launch {
            _uiState.value = UserSongsUiState.Loading
            _uiState.value = when (val result = songRepository.getVisibleSongs(query = query, page = 0, size = 10)) {
                is RepositoryResult.Success -> UserSongsUiState.Success(result.data.content)
                is RepositoryResult.Error -> UserSongsUiState.Error(
                    when (result.code) {
                        401 -> "Tu sesion ya no es valida. Inicia sesion otra vez."
                        403 -> "No tienes permisos para consultar canciones."
                        else -> result.message
                    }
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            loadSongs(query = query.takeIf { it.isNotBlank() })
        }
    }
}
