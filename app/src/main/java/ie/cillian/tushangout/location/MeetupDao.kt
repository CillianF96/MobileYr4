package ie.cillian.tushangout.location

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MeetupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeetup(meetup: Meetup): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMeetup(meetup: Meetup): Long?

    @Query("SELECT * FROM meetup")
    fun getAllMeetups(): LiveData<List<Meetup>>

    @Query("SELECT * FROM meetup WHERE id = :id")
    fun getMeetupById(id: Long): Meetup?

    @Query("DELETE FROM meetup WHERE id = :id")
    fun deleteMeetupById(id: Long)

    @Query("SELECT * FROM meetup WHERE type = :type")
    fun getMeetupsByType(type: String): List<Meetup>

    @Query("SELECT * FROM meetup WHERE location LIKE :location")
    fun getMeetupsByLocation(location: String): List<Meetup>

    @Query("SELECT * FROM meetup WHERE location = :location")
    fun getMeetUpPlaces(location: String): List<Meetup>
}


