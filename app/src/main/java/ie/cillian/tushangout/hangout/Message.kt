package ie.cillian.tushangout.hangout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message
    (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String ="",
    val timestamp: String = "",

    )