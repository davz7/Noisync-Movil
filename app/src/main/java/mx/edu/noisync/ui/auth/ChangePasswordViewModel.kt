package mx.edu.noisync.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.noisync.data.model.ChangePasswordRequest
import mx.edu.noisync.data.repository.RepositoryProvider
import mx.edu.noisync.data.repository.RepositoryResult

sealed class ChangePasswordUiState {
    object Idle : ChangePasswordUiState()
    object Loading : ChangePasswordUiState()
    data class Success(val message: String) : ChangePasswordUiState()
    data class Error(val message: String) : ChangePasswordUiState()
}

class ChangePasswordViewModel : ViewModel() {
    private val profileRepository = RepositoryProvider.profileRepository

    private val _uiState = MutableStateFlow<ChangePasswordUiState>(ChangePasswordUiState.Idle)
    val uiState: StateFlow<ChangePasswordUiState> = _uiState.asStateFlow()

    fun submit(currentPassword: String, newPassword: String, confirmPassword: String) {
        when {
            currentPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank() -> {
                _uiState.value = ChangePasswordUiState.Error("Completa todos los campos")
            }

            newPassword != confirmPassword -> {
                _uiState.value = ChangePasswordUiState.Error("Las contrasenas no coinciden")
            }

            newPassword == currentPassword -> {
                _uiState.value = ChangePasswordUiState.Error("La nueva contrasena debe ser diferente a la actual")
            }

            !isStrongPassword(newPassword) -> {
                _uiState.value = ChangePasswordUiState.Error(
                    "La contrasena debe tener al menos 8 caracteres, una mayuscula y un numero"
                )
            }

            else -> {
                updatePassword(currentPassword, newPassword, confirmPassword)
            }
        }
    }

    fun resetState() {
        _uiState.value = ChangePasswordUiState.Idle
    }

    private fun isStrongPassword(password: String): Boolean {
        val hasUppercase = password.any(Char::isUpperCase)
        val hasDigit = password.any(Char::isDigit)
        return password.length >= 8 && hasUppercase && hasDigit
    }

    private fun updatePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            _uiState.value = ChangePasswordUiState.Loading

            when (
                val result = profileRepository.changePassword(
                    ChangePasswordRequest(
                        currentPassword = currentPassword,
                        newPassword = newPassword,
                        confirmPassword = confirmPassword
                    )
                )
            ) {
                is RepositoryResult.Success -> {
                    _uiState.value = ChangePasswordUiState.Success(result.data.message)
                }

                is RepositoryResult.Error -> {
                    val message = when (result.code) {
                        400 -> "Revisa tu contrasena actual e intenta de nuevo"
                        401 -> "Tu sesion ya no es valida. Inicia sesion otra vez"
                        else -> result.message
                    }
                    _uiState.value = ChangePasswordUiState.Error(message)
                }
            }
        }
    }
}
