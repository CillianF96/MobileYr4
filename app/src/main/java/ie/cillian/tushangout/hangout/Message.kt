package ie.cillian.tushangout.hangout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val senderName: String,
    val messageText: String,
    //val timestamp: Long
    )