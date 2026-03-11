package mx.edu.noisync.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.noisync.data.model.LoginRequest
import mx.edu.noisync.data.model.LoginResponse
import mx.edu.noisync.data.network.RetrofitClient

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val data: LoginResponse) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(identifier: String, password: String) {
        if (identifier.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Completa todos los campos")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val response = RetrofitClient.instance.login(LoginRequest(identifier, password))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _uiState.value = LoginUiState.Success(body)
                    } else {
                        _uiState.value = LoginUiState.Error("Respuesta del servidor vacía")
                    }
                } else {
                    val msg = when (response.code()) {
                        401 -> "Credenciales incorrectas"
                        403 -> "Cuenta no verificada"
                        404 -> "Usuario no encontrado"
                        500 -> "Error del servidor"
                        else -> "Error ${response.code()}"
                    }
                    _uiState.value = LoginUiState.Error(msg)
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error("Sin conexión al servidor")
            }
        }
    }
    
    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}
