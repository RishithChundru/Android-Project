package com.example.project

import android.os.Bundle
import android.widget.ImageView
import android.content.Intent
import android.widget.TextView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class View_Fee : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_fee)
        db = AppDatabase.getDatabase(this)
        dao = db.userDao()
        val email = intent.getStringExtra("email")
        val paidText = findViewById<TextView>(R.id.paidFee)
        val balanceText = findViewById<TextView>(R.id.balanceFee)
        val progressBar = findViewById<ProgressBar>(R.id.feeProgressBar)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon)
        val logoutIcon= findViewById<ImageView>(R.id.logout2)
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
                if (user != null) {
                    val paid = user.feePaid ?: 0
                    val balance = user.balanceFee ?: 40000
                    val percentage = (paid * 100) / 40000
                    paidText.text = "Paid Fee: ₹${user.feePaid ?: 0}"
                    balanceText.text = "Balance Fee: ₹${user.balanceFee ?: 40000}"
                    progressBar.progress = percentage
                }
            }
        }
    }
}