package com.silali.listmaker.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class TaskListTypeConverter {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        @TypeConverter
        @JvmStatic
        fun toString(taskLists: ArrayList<String>): String {
            return Gson().toJson(taskLists)
        }

        @TypeConverter
        @JvmStatic
        fun toArrayList(json: String): ArrayList<String> {
            val objectType = object : TypeToken<ArrayList<String>>() {

            }.type
            return Gson().fromJson<ArrayList<String>>(json, objectType)

        }

        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        @JvmStatic
        fun toOffsetDateTime(value: String?): OffsetDateTime? {
            return value?.let {
                return formatter.parse(value, OffsetDateTime::from)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        @JvmStatic
        fun fromOffsetDateTime(date: OffsetDateTime?): String? {
            return date?.format(formatter)
        }
    }
}