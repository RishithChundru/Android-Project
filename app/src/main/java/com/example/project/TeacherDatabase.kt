package com.example.project

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Teacher::class], version = 2, exportSchema = false)
abstract class TeacherDatabase : RoomDatabase() {
    abstract fun teacherDao(): TeacherDao

    companion object {
        @Volatile
        private var INSTANCE: TeacherDatabase? = null

        fun getDatabase(context: Context): TeacherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TeacherDatabase::class.java,
                    "teacher_database"
                )
               .fallbackToDestructiveMigration()
               .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
