package ie.cillian.tushangout.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
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
    private val auth = FirebaseAuth.getInstance()
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState
    private var userCourse: String = ""

    init {
        fetchUserCourse()
    }

    fun resetState() {
        _uiState.value = HomeUiState.Idle
        userCourse = ""
        android.util.Log.d("HomeViewModel", "State Reset: userCourse cleared")
    }

    private fun fetchUserCourse() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            val currentUser = auth.currentUser
            if (currentUser != null) {
                firestore.collection("users")
                    .document(currentUser.uid)
                    .get()
                    .addOnSuccessListener { document ->
                        userCourse = document.getString("course")?.lowercase() ?: ""
                        android.util.Log.d("HomeViewModel", "Fetched User Course: $userCourse")
                        fetchMeetups()
                    }
                    .addOnFailureListener { exception ->
                        _uiState.value = HomeUiState.Error("Failed to fetch user course: ${exception.message}")
                    }
            } else {
                _uiState.value = HomeUiState.Error("No logged-in user.")
            }
        }
    }

    private fun fetchMeetups() {
        firestore.collection("meetups")
            .get()
            .addOnSuccessListener { result ->
                val allMeetups = result.documents.mapNotNull { document ->
                    document.toObject<Meetup>()
                }
                android.util.Log.d("HomeViewModel", "All Meetups Before Filtering: $allMeetups")

                val filteredMeetups = allMeetups.filter { it.course.lowercase() == userCourse }
                android.util.Log.d("HomeViewModel", "Filtered Meetups for $userCourse: $filteredMeetups")
                _uiState.value = HomeUiState.Success(meetups = filteredMeetups)
            }
            .addOnFailureListener { exception ->
                _uiState.value = HomeUiState.Error("Failed to fetch meetups: ${exception.message}")
            }
    }

}
