package com.example.project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val password: String,
    val percentage: Int? = null,
    val attendance: Int? = null,
    val feePaid: Int? = 0,
    val balanceFee: Int? = 40000,
    val behaviour: String? = null,
    val alertMessage: String? = null
)

