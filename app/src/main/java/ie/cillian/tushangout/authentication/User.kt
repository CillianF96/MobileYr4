package ie.cillian.tushangout.authentication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val course: String = "",
    val password: String = ""

)