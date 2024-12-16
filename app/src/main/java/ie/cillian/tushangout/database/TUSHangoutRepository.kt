package ie.cillian.tushangout.database

import android.app.Application
import androidx.lifecycle.LiveData
import ie.cillian.tushangout.hangout.Message
import ie.cillian.tushangout.location.Meetup

class TUSHangoutRepository (application: Application){

    private val messageDao = TUSHangoutDatabase.getDatabase(application)!!.messageDao()
    private val meetupDao = TUSHangoutDatabase.getDatabase(application)!!.meetUpDao()
    private val userDao = TUSHangoutDatabase.getDatabase(application)!!.userDao()


    suspend fun fetchMessageItems(): LiveData<List<Message>> {
        return messageDao.getAllMessages()
    }

    fun fetchMeetups(): LiveData<List<Meetup>> {
        return meetupDao.getAllMeetups()
    }

    fun fetchMeetupPlaces(location: String): List<Meetup> { //check to ensure it doesnt need to be LiveData<List<Meetup>>
        return meetupDao.getMeetUpPlaces(location)
    }





}