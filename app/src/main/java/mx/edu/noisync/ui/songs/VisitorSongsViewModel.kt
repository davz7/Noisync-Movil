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

sealed class VisitorSongsUiState {
    object Loading : VisitorSongsUiState()
    data class Success(
        val songs: List<SongListItem>,
        val hasMore: Boolean,
        val isLoadingMore: Boolean = false
    ) : VisitorSongsUiState()
    data class Error(val message: String) : VisitorSongsUiState()
}

enum class VisitorSongsFilter {
    ALL,
    RECENT
}

class VisitorSongsViewModel : ViewModel() {
    private val songRepository = RepositoryProvider.songRepository
    private val pageSize = 4
    private var searchJob: Job? = null
    private var currentSongs: List<SongListItem> = emptyList()
    private var currentPage = 0
    private var hasMorePages = true

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
            currentPage = 0
            hasMorePages = true
            _uiState.value = VisitorSongsUiState.Loading
            _uiState.value = when (val result = songRepository.getPublicSongs(query = query, page = 0, size = pageSize)) {
                is RepositoryResult.Success -> {
                    currentSongs = result.data.content
                    hasMorePages = !result.data.last
                    VisitorSongsUiState.Success(
                        songs = applyFilter(currentSongs, _selectedFilter.value),
                        hasMore = hasMorePages
                    )
                }
                is RepositoryResult.Error -> VisitorSongsUiState.Error(result.message)
            }
        }
    }

    fun loadMoreSongs() {
        val query = _searchQuery.value.takeIf { it.isNotBlank() }
        val currentState = _uiState.value as? VisitorSongsUiState.Success ?: return
        if (!currentState.hasMore || currentState.isLoadingMore) {
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoadingMore = true)

            when (val result = songRepository.getPublicSongs(query = query, page = currentPage + 1, size = pageSize)) {
                is RepositoryResult.Success -> {
                    currentPage += 1
                    hasMorePages = !result.data.last
                    currentSongs = currentSongs + result.data.content
                    _uiState.value = VisitorSongsUiState.Success(
                        songs = applyFilter(currentSongs, _selectedFilter.value),
                        hasMore = hasMorePages,
                        isLoadingMore = false
                    )
                }

                is RepositoryResult.Error -> {
                    _uiState.value = currentState.copy(isLoadingMore = false)
                }
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
            _uiState.value = currentState.copy(
                songs = applyFilter(currentSongs, filter)
            )
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
