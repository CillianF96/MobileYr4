package ie.cillian.tushangout.location

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MeetupDao {

    @Insert
    suspend fun saveMeetup(meetup: Meetup)

    @Insert
    suspend fun saveMultipleMeetups(recipeList: List<Meetup>)

    @Query("SELECT * FROM meetup")
    fun getAllMeetups(): LiveData<List<Meetup>>

    @Query("SELECT type, image, latitude, longitude FROM meetup")
    fun getMeetUpPlaces(): LiveData<List<Meetup>>

}