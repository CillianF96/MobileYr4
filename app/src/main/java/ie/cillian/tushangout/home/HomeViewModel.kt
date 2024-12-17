package ie.cillian.tushangout.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Meetup(
    val meetupName: String = "",
    val course: String = "",
    val date: String = "",
    val time: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

sealed class HomeUiState {
    object Idle : HomeUiState()
    object Loading : HomeUiState()
    data class Success(val meetups: List<Meetup>) : HomeUiState()
    data class Error(val errorMessage: String) : HomeUiState()
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val firestore = FirebaseFirestore.getInstance()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchMeetups()
    }

    private fun fetchMeetups() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            firestore.collection("meetups")
                .get()
                .addOnSuccessListener { result ->
                    val meetupList = result.documents.mapNotNull { document ->
                        document.toObject<Meetup>()
                    }
                    _uiState.value = HomeUiState.Success(meetups = meetupList)
                }
                .addOnFailureListener { exception ->
                    _uiState.value = HomeUiState.Error("Failed to fetch meetups: ${exception.message}")
                }
        }
    }
}
