package com.example.project

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUser(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("UPDATE users SET percentage = :percentage WHERE email = :email")
    suspend fun updateMarksByEmail(email: String, percentage: Int)

    @Query("UPDATE users SET attendance = :attendance WHERE email = :email")
    suspend fun updateAttendanceByEmail(email: String, attendance: Int)

    @Query("UPDATE users SET feePaid = :feePaid, balanceFee = :balanceFee WHERE email = :email")
    suspend fun updateFeeStatus(email: String, feePaid: Int, balanceFee: Int)

    @Query("UPDATE users SET behaviour = :behaviour WHERE email = :email")
    suspend fun updateBehaviourByEmail(email: String, behaviour: String)

    @Query("UPDATE users SET password = :newPassword WHERE email = :email")
    suspend fun updatePasswordByEmail(email: String, newPassword: String)

    @Query("UPDATE users SET alertMessage = :message WHERE email = :email")
    suspend fun updateAlertMessage(email: String, message: String)

}
