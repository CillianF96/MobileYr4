package ie.cillian.tushangout.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application)  : AndroidViewModel(application) {

    // A StateFlow to manage the state of the screen (e.g., loading state)
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        // Example of fetching data or preparing something on initialization
        fetchInitialData()
    }

    // Simulated data fetching or preparation logic
    private fun fetchInitialData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            delay(2000) // Simulate a network or database delay
            _uiState.value = HomeUiState.Success(message = "Welcome to TUSHangOut!")
        }
    }
}

// UI State definition for managing the screen state
sealed class HomeUiState {
    object Idle : HomeUiState()
    object Loading : HomeUiState()
    data class Success(val message: String) : HomeUiState()
    data class Error(val errorMessage: String) : HomeUiState()
}
