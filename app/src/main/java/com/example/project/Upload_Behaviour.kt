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

class Upload_Behaviour : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_behaviour)
        db = AppDatabase.getDatabase(this)
        dao = db.userDao()
        val email=findViewById<EditText>(R.id.email4)
        val behaviour=findViewById<EditText>(R.id.behaviour)
        val uploadbtn=findViewById<Button>(R.id.uploadBtn)

        val toolbar = findViewById<Toolbar>(R.id.toolbar10)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon10)
        val logoutIcon= findViewById<ImageView>(R.id.logout13)
        homeIcon.setOnClickListener {
            val i=Intent(this, Teacher_Dashboard::class.java)
            startActivity(i)
        }
        logoutIcon.setOnClickListener {
            val j=Intent(this, Login_Page::class.java)
            startActivity(j)
        }

        uploadbtn.setOnClickListener {
            val Email = email.text.toString().trim()
            val Behaviour = behaviour.text.toString().trim().uppercase()
            if (Email.isEmpty() || Behaviour.isEmpty()) {
                showCustomToast("Fields cannot be empty")
                return@setOnClickListener
            }
            if (Behaviour != "GOOD" && Behaviour != "AVERAGE" && Behaviour != "BAD") {
                showCustomToast("Enter GOOD, AVERAGE, or BAD only")
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val user = dao.getUserByEmail(Email)
                if (user != null) {
                    dao.updateBehaviourByEmail(Email, Behaviour)
                    runOnUiThread {
                        showCustomToast("Behaviour Updated")
                        email.text.clear()
                        behaviour.text.clear()
                    }
                } else {
                    runOnUiThread {
                        showCustomToast("User Not Found")
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