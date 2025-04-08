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
import kotlinx.coroutines.launch

class View_Progress : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_progress)

        db = AppDatabase.getDatabase(this)
        dao = db.userDao()
        val progressText=findViewById<TextView>(R.id.progressmsg)
        val progressbtn=findViewById<Button>(R.id.progress1)
        val Email = intent.getStringExtra("email") ?: ""
        val toolbar = findViewById<Toolbar>(R.id.toolbar12)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon12)
        val logoutIcon= findViewById<ImageView>(R.id.logout15)
        homeIcon.setOnClickListener {
            val i=Intent(this, Dashboard::class.java)
            startActivity(i)
        }
        logoutIcon.setOnClickListener {
            val j=Intent(this, Login_Page::class.java)
            startActivity(j)
        }

        progressbtn.setOnClickListener {
            lifecycleScope.launch{
                val user = dao.getUserByEmail(Email)
                runOnUiThread {
                    if (user != null) {
                        var message = ""

                        if ((user.attendance ?: 0) >= 75) {
                            message += "Attendance: Good\n"
                        } else {
                            message += "Attendance: Low\n"
                        }

                        if ((user.percentage ?: 0) >= 70) {
                            message += "Grades: Good\n"
                        } else {
                            message += "Grades: Needs Improvement\n"
                        }

                        val behavior = user.behaviour ?: "UNKNOWN"
                        message += "Behavior: ${behavior.uppercase()}\n"

                        if ((user.balanceFee ?: 0) <= 0) {
                            message += "Fee Status: Paid"
                        } else {
                            message += "Fee Status: Unpaid"
                        }

                        progressText.text = message
                    } else {
                        progressText.text = "No data found"
                    }
                }
            }
            }
        }
    }
