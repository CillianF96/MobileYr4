package ie.cillian.tushangout.location

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "meetup")
data class Meetup(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val course: String,
    val startingTime: String,
    val date: Long,
    val location: String,
    val description: String = "",
    val createdBy: String = "",
    val type: String,
    val image: String,
    val latitude: Double?,
    val longitude: Double?
)
