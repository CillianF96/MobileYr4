package ie.cillian.tushangout.hangout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ie.cillian.tushangout.database.TUSHangoutDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessageViewModel(application: Application) : AndroidViewModel(application) {
    private val messageDao = TUSHangoutDatabase.getDatabase(application).messageDao()

    val getAllMessages: LiveData<List<Message>> = messageDao.getAllMessages()

     suspend fun insertMessage(senderName: String, messageText: String) {
        return withContext(Dispatchers.IO) {
            //val timestamp = System.currentTimeMillis()
            val message =
                Message(senderName = senderName, messageText = messageText)
            messageDao.insertMessage(message)
        }
    }

//    fun getMessagesBySender(senderName: String, callback: (List<Message>) -> Unit) {
//        viewModelScope.launch {
//            val messages = messageDao.getMessagesBySender(senderName)
//            callback(messages)
//        }
//    }
}