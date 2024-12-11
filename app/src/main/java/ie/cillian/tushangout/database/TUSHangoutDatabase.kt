package ie.cillian.tushangout.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import ie.cillian.tushangout.authentication.User
import ie.cillian.tushangout.hangout.Message
import ie.cillian.tushangout.location.Meetup

@Database(entities=[Message::class, Meetup::class, User::class], version = 1)
@TypeConverter(ArrayListConverter::class)
abstract class TUSHangoutDatabase : RoomDatabase(){






}