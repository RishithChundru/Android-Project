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

class Upload_Fee : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_fee)
        db = AppDatabase.getDatabase(this)
        dao = db.userDao()
        val email = findViewById<EditText>(R.id.email3)
        val feePaidNow = findViewById<EditText>(R.id.feePaidNow)
        val uploadButton = findViewById<Button>(R.id.uploadFeeButton)

        val toolbar = findViewById<Toolbar>(R.id.toolbar9)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon9)
        val logoutIcon= findViewById<ImageView>(R.id.logout12)
        homeIcon.setOnClickListener {
            val i=Intent(this, Teacher_Dashboard::class.java)
            startActivity(i)
        }
        logoutIcon.setOnClickListener {
            val j=Intent(this, Login_Page::class.java)
            startActivity(j)
        }


        uploadButton.setOnClickListener {
            val Email = email.text.toString().trim()
            val FeePaid = feePaidNow.text.toString().toIntOrNull()
            if (Email.isNotEmpty() && FeePaid != null) {
                lifecycleScope.launch {
                    val user = dao.getUserByEmail(Email)
                    if (user != null) {
                        val currentPaid = user.feePaid ?: 0
                        val totalPaid = currentPaid + FeePaid

                        if (totalPaid > 40000) {
                            runOnUiThread {
                                showCustomToast("Total fee paid cannot exceed ₹40000")
                            }
                        } else {
                            val newTotalPaid = totalPaid
                            val balance = maxOf(40000 - newTotalPaid, 0)

                            dao.updateFeeStatus(Email, newTotalPaid, balance)

                            runOnUiThread {
                                showCustomToast("Fee Updated")
                                email.text.clear()
                                feePaidNow.text.clear()
                            }
                        }
                    } else {
                        runOnUiThread {
                            showCustomToast("User Not Found")
                        }
                    }
                }
            } else {
                showCustomToast("Invalid email or fee input")
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