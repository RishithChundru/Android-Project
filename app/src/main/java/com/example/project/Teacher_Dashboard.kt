package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Teacher_Dashboard : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_dashboard)
        val markAttendance=findViewById<ImageButton>(R.id.markAttendance)
        val lectures=findViewById<ImageButton>(R.id.lectures)
        val feeupdates=findViewById<ImageButton>(R.id.feeupdate)
        val studentbehaviour=findViewById<ImageButton>(R.id.StudentBehaviour)
        val sendalerts=findViewById<ImageButton>(R.id.SendAlerts)
        val gradeStudents=findViewById<ImageButton>(R.id.GradeStudents)
        val changePassword=findViewById<ImageButton>(R.id.ChangePassword1)
        val logout=findViewById<ImageButton>(R.id.Logout1)
        markAttendance.setOnClickListener {
            val a= Intent(this, MarkAttendance::class.java)
            startActivity(a)
        }
        lectures.setOnClickListener {
            val b= Intent(this, Lectures::class.java)
            startActivity(b)
        }
        feeupdates.setOnClickListener {
            val c=Intent(this, Upload_Fee::class.java)
            startActivity(c)
        }
        studentbehaviour.setOnClickListener {
            val d=Intent(this, Upload_Behaviour::class.java)
            startActivity(d)
        }
        sendalerts.setOnClickListener {
            val e=Intent(this, Check_Risk::class.java)
            startActivity(e)
        }
        gradeStudents.setOnClickListener {
            val f= Intent(this, Upload_Marks::class.java)
            startActivity(f)
        }
        changePassword.setOnClickListener {
            val g=Intent(this, Change_Teacher_Password::class.java)
            startActivity(g)
        }
        logout.setOnClickListener {
            val h= Intent(this, Login_Page::class.java)
            showCustomToast("Logged Out Successfully")
            startActivity(h)
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