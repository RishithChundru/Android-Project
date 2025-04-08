package com.example.project

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Upload_Marks : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_marks)
        db = AppDatabase.getDatabase(this)
        dao = db.userDao()
        val email = findViewById<EditText>(R.id.email)
        val percentage = findViewById<EditText>(R.id.percentage)
        val uploadButton = findViewById<Button>(R.id.uploadButton)

        val toolbar = findViewById<Toolbar>(R.id.toolbar8)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon8)
        val logoutIcon= findViewById<ImageView>(R.id.logout11)
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
            val Percentage = percentage.text.toString().toIntOrNull()
            if (Email.isNotEmpty() && Percentage != null) {
                lifecycleScope.launch {
                    try {
                        val user = dao.getUserByEmail(Email)
                        if (user != null) {
                            dao.updateMarksByEmail(Email, Percentage)
                            runOnUiThread {
                                showCustomToast("Marks Uploaded Successfully")
                                email.text.clear()
                                percentage.text.clear()
                            }
                        } else {
                            runOnUiThread {
                                showCustomToast("User not found!")
                            }
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@Upload_Marks, "Error: ${e.message}", Toast.LENGTH_LONG).show()
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
