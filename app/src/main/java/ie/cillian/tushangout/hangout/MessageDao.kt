package ie.cillian.tushangout.hangout

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message): Long

    @Query("SELECT * FROM messages WHERE id = :id")
    fun getMessageById(id: Long): Message?

    @Query("SELECT * FROM messages")
    fun getAllMessages(): LiveData<List<Message>>
}


