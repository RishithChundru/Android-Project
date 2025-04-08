package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class View_Alerts : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_alerts)
        db = AppDatabase.getDatabase(this)
        dao = db.userDao()
        val toolbar = findViewById<Toolbar>(R.id.toolbar4)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon4)
        val logoutIcon= findViewById<ImageView>(R.id.logout6)
        homeIcon.setOnClickListener {
            val i=Intent(this, Dashboard::class.java)
            startActivity(i)
        }
        logoutIcon.setOnClickListener {
            val j=Intent(this, Login_Page::class.java)
            startActivity(j)
        }
        val alertmsg=findViewById<TextView>(R.id.Alertmsg)
        val email=intent.getStringExtra("email")?: ""
        if (email.isNotEmpty()) {
            lifecycleScope.launch {
                val user = dao.getUserByEmail(email)
                runOnUiThread {
                    if (user != null) {
                        val alertMessage = user.alertMessage

                        if (!alertMessage.isNullOrEmpty()) {
                            alertmsg.text = alertMessage
                        } else {
                            alertmsg.text = "YOU ARE GOOD AT YOUR ACADEMICS"
                        }
                    } else {
                        alertmsg.text = "Student not found."
                    }
                }
            }
        }
    }
}