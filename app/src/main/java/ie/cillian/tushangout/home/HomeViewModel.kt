package ie.cillian.tushangout.home

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application)  : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchInitialData()
    }

    private fun fetchInitialData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            delay(2000)
            _uiState.value = HomeUiState.Success(message = "Welcome to TUSHangOut!")
        }
    }


    private val meetups = mutableListOf<Pair<String, LatLng>>()

    fun saveMeetupLocation(location: LatLng) {
        val meetupName = "Example Meetup"
        meetups.add(meetupName to location)
        println("Location saved for $meetupName: ${location.latitude}, ${location.longitude}")
    }

    fun getMeetups(): List<Pair<String, LatLng>> {
        return meetups
    }


    val homeItems = mutableStateListOf("Default Item 1", "Default Item 2", "Default Item 3")

    fun updateFirstItemWithLocation(location: LatLng) {
        val newItem = "Meetup at Lat: ${location.latitude}, Lng: ${location.longitude}"
        if (homeItems.isNotEmpty()) {
            homeItems[0] = newItem
        } else {
            homeItems.add(newItem)
        }
    }







}

sealed class HomeUiState {
    object Idle : HomeUiState()
    object Loading : HomeUiState()
    data class Success(val message: String) : HomeUiState()
    data class Error(val errorMessage: String) : HomeUiState()
}
