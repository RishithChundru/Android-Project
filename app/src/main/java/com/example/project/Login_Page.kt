package com.example.project

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Login_Page : AppCompatActivity() {
    private lateinit var studentDatabase: AppDatabase
    private lateinit var teacherDatabase: TeacherDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val Email = findViewById<EditText>(R.id.Email)
        val Password = findViewById<EditText>(R.id.Password)
        val Login = findViewById<Button>(R.id.Login)
        val Register = findViewById<TextView>(R.id.Register)

        studentDatabase = AppDatabase.getDatabase(this)
        teacherDatabase = TeacherDatabase.getDatabase(this)

        Register.setOnClickListener {
            val i = Intent(this, Register_Page::class.java)
            startActivity(i)
        }

        Login.setOnClickListener {
            val email = Email.text.toString()
            val password = Password.text.toString()

            lifecycleScope.launch {
                if (isTeacherEmail(email)) {
                    val teacher = teacherDatabase.teacherDao().getTeacherByEmail(email)
                    if (teacher != null && teacher.password == password) {
                        runOnUiThread {
                            showCustomToast("Login Successful as Teacher")
                            startActivity(Intent(this@Login_Page, Teacher_Dashboard::class.java))
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@Login_Page, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    val user = studentDatabase.userDao().getUserByEmail(email)
                    if (user != null && user.password == password) {
                        runOnUiThread {
                            showCustomToast("Login Successful as Student")
                            val intent = Intent(this@Login_Page, Dashboard::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@Login_Page, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun isTeacherEmail(email: String): Boolean {
        return email.endsWith("@myschool.in")
    }

    private fun showCustomToast(message: String) {
        val inflater = layoutInflater
        val layout: View = inflater.inflate(R.layout.registercustomtoast, null)

        val tv = layout.findViewById<TextView>(R.id.txtVw)
        tv.text = message

        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 350)
        toast.duration = Toast.LENGTH_SHORT
        toast.setView(layout)
        toast.show()
    }
}
