package ie.cillian.tushangout.authentication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun saveUser(user: User)

    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>>


}