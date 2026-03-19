package mx.edu.noisync.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.noisync.data.model.UserProfile
import mx.edu.noisync.data.repository.RepositoryProvider
import mx.edu.noisync.data.repository.RepositoryResult

sealed class UserInfoUiState {
    object Loading : UserInfoUiState()
    data class Success(val profile: UserProfile) : UserInfoUiState()
    data class Error(val message: String) : UserInfoUiState()
}

class UserInfoViewModel : ViewModel() {
    private val profileRepository = RepositoryProvider.profileRepository

    private val _uiState = MutableStateFlow<UserInfoUiState>(UserInfoUiState.Loading)
    val uiState: StateFlow<UserInfoUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = UserInfoUiState.Loading
            _uiState.value = when (val result = profileRepository.getProfile()) {
                is RepositoryResult.Success -> UserInfoUiState.Success(result.data)
                is RepositoryResult.Error -> UserInfoUiState.Error(result.message)
            }
        }
    }
}
