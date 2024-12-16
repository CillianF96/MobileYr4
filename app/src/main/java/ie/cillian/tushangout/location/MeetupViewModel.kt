package ie.cillian.tushangout.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ie.cillian.tushangout.database.TUSHangoutDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MeetupViewModel(application: Application) : AndroidViewModel(application) {
    private val meetupDao = TUSHangoutDatabase.getDatabase(application).meetUpDao()

    val getAllMeetups: LiveData<List<Meetup>> = meetupDao.getAllMeetups()

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
    ) : Long? {
        return withContext(Dispatchers.IO) {
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
            meetupDao.insertMeetup(meetup)
        }
    }

    fun deleteMeetupById(meetupId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            meetupDao.deleteMeetupById(meetupId)
        }
    }

    fun getMeetupsByType(type: String, callback: (List<Meetup>) -> Unit) {
        viewModelScope.launch {
            val meetups = withContext(Dispatchers.IO) {
                meetupDao.getMeetupsByType(type)
            }
            callback(meetups)
        }
    }
}
