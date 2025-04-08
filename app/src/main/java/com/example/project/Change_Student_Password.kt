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

class Change_Student_Password : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_student_password)

        db = AppDatabase.getDatabase(this)
        dao = db.userDao()

        val email=findViewById<EditText>(R.id.emailchange)
        val oldpassword=findViewById<EditText>(R.id.oldPasswordInput)
        val newpassword=findViewById<EditText>(R.id.newPasswordInput)
        val changebutton=findViewById<Button>(R.id.changePasswordButton)

        val toolbar = findViewById<Toolbar>(R.id.toolbar5)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon5)
        val logoutIcon= findViewById<ImageView>(R.id.logout7)
        homeIcon.setOnClickListener {
            val i=Intent(this, Dashboard::class.java)
            startActivity(i)
        }
        logoutIcon.setOnClickListener {
            val j=Intent(this, Login_Page::class.java)
            startActivity(j)
        }
        changebutton.setOnClickListener {
            val Email = email.text.toString().trim()
            val OldPassword = oldpassword.text.toString()
            val NewPassword = newpassword.text.toString()

            if (Email.isNotEmpty() && OldPassword.isNotEmpty() && NewPassword.isNotEmpty()) {
                lifecycleScope.launch {
                    val user = dao.getUser(Email, OldPassword)
                    if (user != null) {
                        if (OldPassword == NewPassword) {
                            runOnUiThread {
                                showCustomToast("Old and new passwords cannot be the same")
                            }
                        } else {
                            dao.updatePasswordByEmail(Email, NewPassword)
                            runOnUiThread {
                                showCustomToast("Password Updated")
                                email.text.clear()
                                oldpassword.text.clear()
                                newpassword.text.clear()
                                startActivity(Intent(this@Change_Student_Password, Login_Page::class.java))
                            }
                        }
                    } else {
                        runOnUiThread {
                            showCustomToast("Invalid Credentials")
                        }
                    }
                }
            } else {
                showCustomToast("All fields are required")
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