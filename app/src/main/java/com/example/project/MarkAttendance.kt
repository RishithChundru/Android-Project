package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.project.Upload_Marks
import kotlinx.coroutines.launch

class MarkAttendance : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_attendance)
        db = AppDatabase.getDatabase(this)
        dao = db.userDao()
        val email=findViewById<EditText>(R.id.email1)
        val attendance_percentage=findViewById<EditText>(R.id.percentage1)
        val upload_button=findViewById<Button>(R.id.uploadButton1)

        val toolbar = findViewById<Toolbar>(R.id.toolbar7)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon7)
        val logoutIcon= findViewById<ImageView>(R.id.logout10)
        homeIcon.setOnClickListener {
            val i=Intent(this, Teacher_Dashboard::class.java)
            startActivity(i)
        }
        logoutIcon.setOnClickListener {
            val j=Intent(this, Login_Page::class.java)
            startActivity(j)
        }

        upload_button.setOnClickListener {
            val Email = email.text.toString().trim()
            val Attendance = attendance_percentage.text.toString().toIntOrNull()

            if (Email.isNotEmpty() && Attendance != null) {
                lifecycleScope.launch {
                    val user = dao.getUserByEmail(Email)
                    if (user != null) {
                        dao.updateAttendanceByEmail(Email, Attendance)
                        runOnUiThread {
                            showCustomToast("Attendance uploaded")
                            email.text.clear()
                            attendance_percentage.text.clear()
                        }
                    } else {
                        runOnUiThread {
                            showCustomToast("User not found!")
                        }
                    }
                }
            }
        }
    }
    private fun showCustomToast(message: String) {
        val inflater = layoutInflater
        val layout: View = inflater.inflate(R.layout.registercustomtoast, null)

        val tv = layout.findViewById<TextView>(R.id.txtVw)
        tv.text = message

        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 650)
        toast.duration = Toast.LENGTH_SHORT
        toast.setView(layout)
        toast.show()
    }
}