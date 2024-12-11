package ie.cillian.tushangout.hangout

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MessageDao {

    @Insert
    suspend fun saveMessage(message: Message)

    @Insert
    suspend fun saveMultipleMessages(messageList: List<Message>)

    @Query("SELECT * FROM message")
    fun getAllMessages(): LiveData<List<Message>>






}