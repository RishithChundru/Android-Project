package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ViewMarks : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    private lateinit var viewPercentage: TextView
    private var studentEmail: String? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var innertext: TextView
    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_marks)
        db = AppDatabase.getDatabase(this)
        dao = db.userDao()
        studentEmail = intent.getStringExtra("email") ?: ""
        viewPercentage = findViewById(R.id.viewPercentage)
        progressBar = findViewById(R.id.progressBar)
        innertext=findViewById(R.id.innerPercentage)

        val toolbar = findViewById<Toolbar>(R.id.toolbar1)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon1)
        val logoutIcon= findViewById<ImageView>(R.id.logout3)
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
                    if (student != null && student.percentage != null) {
                        val percentage = student.percentage.toInt()
                        viewPercentage.text = "Overall Percentage: $percentage%"
                        progressBar.progress = percentage
                        innertext.text="$percentage%"

                        val progressDrawable = progressBar.progressDrawable.mutate()
                        val color = when {
                            percentage < 40 -> Color.RED
                            percentage in 40..75 -> Color.YELLOW
                            else -> Color.GREEN
                        }
                        DrawableCompat.setTint(progressDrawable, color)
                        progressBar.progressDrawable = progressDrawable

                    } else {
                        viewPercentage.text = "Overall Percentage: Not Uploaded"
                        progressBar.progress = 0
                    }
                }
        }
    }
}