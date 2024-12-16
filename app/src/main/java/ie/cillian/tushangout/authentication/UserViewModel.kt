package ie.cillian.tushangout.authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ie.cillian.tushangout.database.TUSHangoutDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = TUSHangoutDatabase.getDatabase(application).userDao()
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    fun saveUser(user: User) {
        viewModelScope.launch {
            userDao.saveUser(user)
        }
    }

    suspend fun getUserByUsername(username: String) {
        return withContext(Dispatchers.IO) {
            userDao.getUserByUsername(username)
        }
    }
}
