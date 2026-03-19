package mx.edu.noisync.ui.visitor

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

sealed class VisitorSongsUiState {
    object Loading : VisitorSongsUiState()
    data class Success(val songs: List<SongListItem>) : VisitorSongsUiState()
    data class Error(val message: String) : VisitorSongsUiState()
}

enum class VisitorSongsFilter {
    ALL,
    RECENT
}

class VisitorSongsViewModel : ViewModel() {
    private val songRepository = RepositoryProvider.songRepository
    private var searchJob: Job? = null
    private var currentSongs: List<SongListItem> = emptyList()

    private val _uiState = MutableStateFlow<VisitorSongsUiState>(VisitorSongsUiState.Loading)
    val uiState: StateFlow<VisitorSongsUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedFilter = MutableStateFlow(VisitorSongsFilter.ALL)
    val selectedFilter: StateFlow<VisitorSongsFilter> = _selectedFilter.asStateFlow()

    init {
        loadSongs()
    }

    fun loadSongs(query: String? = null) {
        viewModelScope.launch {
            _uiState.value = VisitorSongsUiState.Loading
            _uiState.value = when (val result = songRepository.getPublicSongs(query = query, page = 0, size = 10)) {
                is RepositoryResult.Success -> {
                    currentSongs = result.data.content
                    VisitorSongsUiState.Success(applyFilter(currentSongs, _selectedFilter.value))
                }
                is RepositoryResult.Error -> VisitorSongsUiState.Error(result.message)
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

    fun selectFilter(filter: VisitorSongsFilter) {
        _selectedFilter.value = filter
        val currentState = _uiState.value
        if (currentState is VisitorSongsUiState.Success) {
            _uiState.value = VisitorSongsUiState.Success(applyFilter(currentSongs, filter))
        }
    }

    private fun applyFilter(
        songs: List<SongListItem>,
        filter: VisitorSongsFilter
    ): List<SongListItem> {
        return when (filter) {
            VisitorSongsFilter.ALL -> songs
            VisitorSongsFilter.RECENT -> songs
                .sortedByDescending { it.createdAt.orEmpty() }
                .take(5)
        }
    }
}
