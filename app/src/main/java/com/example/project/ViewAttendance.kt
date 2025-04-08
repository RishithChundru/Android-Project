package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ViewAttendance : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    private lateinit var viewAttendance: TextView
    private var studentEmail: String? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var innertext: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_attendance)
        db = AppDatabase.getDatabase(this)
        dao = db.userDao()
        studentEmail = intent.getStringExtra("email") ?: ""
        viewAttendance = findViewById(R.id.viewPercentage1)
        progressBar = findViewById(R.id.progressBar1)
        innertext=findViewById(R.id.innerPercentage1)

        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon2)
        val logoutIcon= findViewById<ImageView>(R.id.logout4)
        homeIcon.setOnClickListener {
            val i=Intent(this, Dashboard::class.java)
            startActivity(i)
        }
        logoutIcon.setOnClickListener {
            val j=Intent(this, Login_Page::class.java)
            startActivity(j)
        }
        lifecycleScope.launch {
            val student = dao.getUserByEmail(studentEmail.toString())
            runOnUiThread {
                if (student != null && student.attendance != null) {
                    val attendance = student.attendance.toInt()
                    viewAttendance.text = "Overall Attendance: $attendance%"
                    progressBar.progress = attendance
                    innertext.text="$attendance%"


                    val progressDrawable = progressBar.progressDrawable.mutate()
                    val color = when {
                        attendance < 60 -> Color.RED
                        attendance in 60..75 -> Color.YELLOW
                        else -> Color.GREEN
                    }
                    DrawableCompat.setTint(progressDrawable, color)
                    progressBar.progressDrawable = progressDrawable

                } else {
                    viewAttendance.text = "Overall Attendance: Not Uploaded"
                    progressBar.progress = 0
                }
            }
        }
    }
}