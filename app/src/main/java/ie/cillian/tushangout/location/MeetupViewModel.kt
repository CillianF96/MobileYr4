package ie.cillian.tushangout.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import ie.cillian.tushangout.database.TUSHangoutDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MeetupViewModel(application: Application) : AndroidViewModel(application) {
    private val meetupDao = TUSHangoutDatabase.getDatabase(application).meetUpDao()
    private val firestore = FirebaseFirestore.getInstance()
    private val collectionRef = firestore.collection("meetups")

    val getAllMeetups: LiveData<List<Meetup>> = meetupDao.getAllMeetups()

    // Sync local Room data to Firestore
    fun syncLocalToFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            val localMeetups = meetupDao.getAllMeetups().value ?: emptyList()
            localMeetups.forEach { meetup ->
                collectionRef.document(meetup.id.toString()).set(meetup)
            }
        }
    }

    // Sync Firestore data to local Room
    fun syncFirestoreToLocal() {
        collectionRef.get()
            .addOnSuccessListener { documents ->
                viewModelScope.launch(Dispatchers.IO) {
                    for (document in documents) {
                        val meetup = document.toObject(Meetup::class.java)
                        meetupDao.insertMeetup(meetup)
                    }
                }
            }
            .addOnFailureListener { exception ->
                println("Error fetching Firestore data: ${exception.message}")
            }
    }

    suspend fun insertMeetup(
        name: String,
        course: String,
        startingTime: String,
        date: Long,
        location: String,
        description: String = "",
        createdBy: String = "",
        type: String,
        image: String,
        latitude: Double? = null,
        longitude: Double? = null
    ): Long? {
        val meetup = Meetup(
            name = name,
            course = course,
            startingTime = startingTime,
            date = date,
            location = location,
            description = description,
            createdBy = createdBy,
            type = type,
            image = image,
            latitude = latitude,
            longitude = longitude
        )

        return withContext(Dispatchers.IO) {
            val id = meetupDao.insertMeetup(meetup)
            collectionRef.document(id.toString()).set(meetup)
            id
        }
    }
}

