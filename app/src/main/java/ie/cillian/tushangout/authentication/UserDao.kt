package ie.cillian.tushangout.authentication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    // Insert user and return the generated primary key

    @Insert
    fun saveUser(user: User): Long

    // Fetch user by username
    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String): User?

    // Fetch all users
    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>



}
