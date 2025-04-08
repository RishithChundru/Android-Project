package com.example.project

import androidx.room.*

@Dao
interface TeacherDao {
    @Insert
    suspend fun insertTeacher(teacher: Teacher)

    @Query("SELECT * FROM teachers WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getTeacher(email: String, password: String): Teacher?

    @Query("SELECT * FROM teachers WHERE email = :email LIMIT 1")
    suspend fun getTeacherByEmail(email: String): Teacher?

    @Query("UPDATE teachers SET password = :newPassword WHERE email = :email")
    suspend fun updateTeacherPasswordByEmail(email: String, newPassword: String)
}
