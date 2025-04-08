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
import com.example.project.Change_Student_Password
import kotlinx.coroutines.launch

class Change_Teacher_Password : AppCompatActivity() {
    private lateinit var db: TeacherDatabase
    private lateinit var dao: TeacherDao
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_teacher_password)
        db = TeacherDatabase.getDatabase(this)
        dao = db.teacherDao()

        val email=findViewById<EditText>(R.id.emailchange1)
        val oldpassword=findViewById<EditText>(R.id.oldPasswordInput1)
        val newpassword=findViewById<EditText>(R.id.newPasswordInput1)
        val changebtn=findViewById<Button>(R.id.changePasswordButton1)

        val toolbar = findViewById<Toolbar>(R.id.toolbar6)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon6)
        val logoutIcon= findViewById<ImageView>(R.id.logout9)
        homeIcon.setOnClickListener {
            val i=Intent(this, Teacher_Dashboard::class.java)
            startActivity(i)
        }
        logoutIcon.setOnClickListener {
            val j=Intent(this, Login_Page::class.java)
            startActivity(j)
        }

        changebtn.setOnClickListener {
            val Email = email.text.toString().trim()
            val OldPassword = oldpassword.text.toString()
            val NewPassword = newpassword.text.toString()

            if (!Email.endsWith("@myschool.in")) {
                showCustomToast("Email must end with @myschool.in")
                return@setOnClickListener
            }
            if (OldPassword == NewPassword) {
                showCustomToast("New password must be different from old password")
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val teacher = dao.getTeacher(Email, OldPassword)
                if (teacher != null) {
                    dao.updateTeacherPasswordByEmail(Email, NewPassword)
                    runOnUiThread {
                        showCustomToast("Password updated successfully")
                        email.text.clear()
                        oldpassword.text.clear()
                        newpassword.text.clear()
                        startActivity(Intent(this@Change_Teacher_Password, Login_Page::class.java))
                    }
                } else {
                    runOnUiThread {
                        showCustomToast("Invalid email or old password")
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
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 850)
        toast.duration = Toast.LENGTH_SHORT
        toast.setView(layout)
        toast.show()
    }
}