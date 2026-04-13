package mx.edu.noisync.ui.songs

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

enum class UserSongsFilter {
    ALL,
    RECENT,
    PRIVATE
}

enum class UserSongsMode {
    GENERAL,
    MY_SONGS
}

class UserSongsViewModel : ViewModel() {
    private val songRepository = RepositoryProvider.songRepository
    private val pageSize = 50
    private var searchJob: Job? = null
    private var currentMode = UserSongsMode.GENERAL
    private var currentSongs: List<SongListItem> = emptyList()

    private val _uiState = MutableStateFlow<UserSongsUiState>(UserSongsUiState.Loading)
    val uiState: StateFlow<UserSongsUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedFilter = MutableStateFlow(UserSongsFilter.ALL)
    val selectedFilter: StateFlow<UserSongsFilter> = _selectedFilter.asStateFlow()

    fun setMode(mode: UserSongsMode) {
        val shouldReload = currentMode != mode || currentSongs.isEmpty()
        currentMode = mode
        if (shouldReload) {
            loadSongs(query = _searchQuery.value.takeIf { it.isNotBlank() })
        }
    }

    fun loadSongs(query: String? = null) {
        viewModelScope.launch {
            _uiState.value = UserSongsUiState.Loading
            _uiState.value = when (val result = loadSongsForCurrentMode(query)) {
                is RepositoryResult.Success -> {
                    currentSongs = result.data
                    UserSongsUiState.Success(applyFilter(currentSongs, _selectedFilter.value))
                }
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

    fun selectFilter(filter: UserSongsFilter) {
        _selectedFilter.value = filter
        val currentState = _uiState.value
        if (currentState is UserSongsUiState.Success) {
            _uiState.value = UserSongsUiState.Success(applyFilter(currentSongs, filter))
        }
    }

    private suspend fun loadSongsForCurrentMode(query: String?): RepositoryResult<List<SongListItem>> {
        return when (currentMode) {
            UserSongsMode.GENERAL -> loadAllPages { page, size ->
                songRepository.getPublicSongs(query = query, page = page, size = size)
            }

            UserSongsMode.MY_SONGS -> loadAllPages { page, size ->
                songRepository.getVisibleSongs(query = query, page = page, size = size)
            }
        }
    }

    private suspend fun loadAllPages(
        request: suspend (page: Int, size: Int) -> RepositoryResult<mx.edu.noisync.data.repository.PageResult<SongListItem>>
    ): RepositoryResult<List<SongListItem>> {
        val loadedSongs = mutableListOf<SongListItem>()
        var page = 0

        while (true) {
            when (val result = request(page, pageSize)) {
                is RepositoryResult.Success -> {
                    loadedSongs += result.data.content
                    if (result.data.last) {
                        return RepositoryResult.Success(loadedSongs)
                    }
                    page += 1
                }
                is RepositoryResult.Error -> return result
            }
        }
    }

    private fun applyFilter(
        songs: List<SongListItem>,
        filter: UserSongsFilter
    ): List<SongListItem> {
        return when (currentMode) {
            UserSongsMode.GENERAL -> songs.filter { it.isPublic }
            UserSongsMode.MY_SONGS -> when (filter) {
                UserSongsFilter.ALL -> songs.filter { it.isPublic }
                UserSongsFilter.RECENT -> songs.sortedByDescending { it.createdAt.orEmpty() }
                UserSongsFilter.PRIVATE -> songs.filter { !it.isPublic }
            }
        }
    }

}
