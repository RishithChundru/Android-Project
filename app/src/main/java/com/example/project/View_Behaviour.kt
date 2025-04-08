package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class View_Behaviour : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_behaviour)
        db = AppDatabase.getDatabase(this)
        dao = db.userDao()
        val viewBehaviour=findViewById<TextView>(R.id.viewbehaviour)
        val ratingBar = findViewById<RatingBar>(R.id.behaviourRatingBar)
        val email = intent.getStringExtra("email")

        val toolbar = findViewById<Toolbar>(R.id.toolbar3)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon3)
        val logoutIcon= findViewById<ImageView>(R.id.logout5)
        homeIcon.setOnClickListener {
            val i=Intent(this, Dashboard::class.java)
            startActivity(i)
        }
        logoutIcon.setOnClickListener {
            val j=Intent(this, Login_Page::class.java)
            startActivity(j)
        }
        lifecycleScope.launch {
            val user = dao.getUserByEmail(email ?: "")
            runOnUiThread {
                if(user!=null) {
                    val behaviour = user.behaviour ?: "Not Uploaded"
                    viewBehaviour.text = "Behaviour: $behaviour"
                    val rating = when (behaviour.uppercase()) {
                        "GOOD" -> 5.0f
                        "AVERAGE" -> 2.5f
                        "BAD" -> 0.5f
                        else -> 0.0f
                    }
                    ratingBar.rating = rating
                }
            }
        }
    }
}