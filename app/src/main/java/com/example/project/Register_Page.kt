package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Register_Page : AppCompatActivity() {
    private lateinit var studentdatabase: AppDatabase
    private lateinit var teacherDatabase: TeacherDatabase
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        val Name = findViewById<EditText>(R.id.name)
        val Email = findViewById<EditText>(R.id.email)
        val Password = findViewById<EditText>(R.id.password)
        val Register = findViewById<Button>(R.id.register)
        val login_back=findViewById<Button>(R.id.loginback)
        studentdatabase = AppDatabase.getDatabase(this)
        teacherDatabase= TeacherDatabase.getDatabase(this)
        login_back.setOnClickListener{
            val i=Intent(this,Login_Page::class.java)
            startActivity(i)
        }
        Register.setOnClickListener {
            val name = Name.text.toString()
            val email = Email.text.toString()
            val password = Password.text.toString()

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPassword(password)) {
                Toast.makeText(this, "Password must be at least 8 characters, include an uppercase, lowercase, number, and special character", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    if (isTeacherEmail(email)) {
                        val existingTeacher = teacherDatabase.teacherDao().getTeacherByEmail(email)
                        if (existingTeacher != null) {
                            runOnUiThread {
                                showCustomToast("Teacher already registered! Redirecting to login...")
                                startActivity(Intent(this@Register_Page, Login_Page::class.java))
                            }
                        } else {
                            val newTeacher = Teacher(name = name, email = email, password = password)
                            teacherDatabase.teacherDao().insertTeacher(newTeacher)
                            runOnUiThread {
                                showCustomToast("Registration Successful as Teacher")
                                startActivity(Intent(this@Register_Page, Login_Page::class.java))
                            }
                        }
                    } else {
                        val existingUser = studentdatabase.userDao().getUserByEmail(email)
                        if (existingUser != null) {
                            runOnUiThread {
                                showCustomToast("User already registered! Redirecting to login...")
                                startActivity(Intent(this@Register_Page, Login_Page::class.java))
                            }
                        } else {
                            val newUser = User(name = name, email = email, password = password)
                            studentdatabase.userDao().insertUser(newUser)
                            runOnUiThread {
                                showCustomToast("Registration Successful as Student")
                                startActivity(Intent(this@Register_Page, Login_Page::class.java))
                            }
                        }
                    }
                }
            }
            else {
                showCustomToast("Please fill all fields")
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }


    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8 && password.any { it.isUpperCase() } && password.any { it.isLowerCase() } && password.any { it.isDigit() } && password.any { !it.isLetterOrDigit() }
    }

    private fun isTeacherEmail(email: String): Boolean {
        return email.endsWith("@myschool.in")
    }

    private fun showCustomToast(message: String) {
        val inflater = layoutInflater
        val layout: View = inflater.inflate(R.layout.registercustomtoast,null)

        val tv = layout.findViewById<TextView>(R.id.txtVw)
        tv.text = message

        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 350)
        toast.duration = Toast.LENGTH_SHORT
        toast.setView(layout)
        toast.show()
    }
}
