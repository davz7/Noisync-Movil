package mx.edu.noisync.ui.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class ChangePasswordUiState {
    object Idle : ChangePasswordUiState()
    data class Error(val message: String) : ChangePasswordUiState()
}

class ChangePasswordViewModel : ViewModel() {

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
                _uiState.value = ChangePasswordUiState.Error(
                    "El envio al backend se conectara en la siguiente fase."
                )
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
}
