package ie.cillian.tushangout.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ie.cillian.tushangout.authentication.User
import ie.cillian.tushangout.authentication.UserDao
import ie.cillian.tushangout.hangout.Message
import ie.cillian.tushangout.hangout.MessageDao
import ie.cillian.tushangout.location.Meetup
import ie.cillian.tushangout.location.MeetupDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Message::class, Meetup::class, User::class], version = 1)
@TypeConverters(ArrayListConverter::class)
abstract class TUSHangoutDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun meetUpDao(): MeetupDao

    companion object {
        private var instance: TUSHangoutDatabase? = null
        private val coroutineScope = CoroutineScope(Dispatchers.IO)

        @Synchronized
        fun getDatabase(context: Context): TUSHangoutDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    TUSHangoutDatabase::class.java,
                    "tushangout_database"
                )
                    .addCallback(roomDatabaseCallback(context))
                    .build()
            }
            return instance
        }

        private fun roomDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    coroutineScope.launch {
                        // seedDatabase(context, getDatabase(context))
                    }
                }
            }
        }
    }
}
