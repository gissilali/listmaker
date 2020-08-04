package com.silali.listmaker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.silali.listmaker.data.model.TaskList
import com.silali.listmaker.data.model.TaskListDao

@Database(entities = [TaskList::class], version = 1, exportSchema = false)
abstract class TaskListDatabase : RoomDatabase() {
    abstract val taskListDao : TaskListDao

    companion object {
        @Volatile
        private var INSTANCE : TaskListDatabase? = null

        fun getInstance(context : Context) : TaskListDatabase? {
            var instance =
                INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskListDatabase::class.java,
                    "task_list_database"
                ).build()
            }

            return instance
        }

    }
}