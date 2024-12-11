package ie.cillian.tushangout.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArrayListConverter {

    @TypeConverter
    fun fromStringArray(value: List<String>): String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArray(value: String): List<String>{
        val listType = object: TypeToken<List<String>>(){}.type
        return Gson().fromJson(value, listType);
    }

}