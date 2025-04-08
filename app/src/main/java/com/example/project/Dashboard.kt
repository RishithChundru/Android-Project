package com.example.project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        var attendance=findViewById<ImageButton>(R.id.Attendance)
        var grades=findViewById<ImageButton>(R.id.Grades)
        var fee=findViewById<ImageButton>(R.id.Fees)
        var behaviour=findViewById<ImageButton>(R.id.Behaviour)
        var alerts=findViewById<ImageButton>(R.id.Alerts)
        var progress=findViewById<ImageButton>(R.id.Progress)
        var changepassword=findViewById<ImageButton>(R.id.ChangePassword)
        var logout=findViewById<ImageButton>(R.id.Logout)
        var email: String=  intent.getStringExtra("email") ?: ""

        attendance.setOnClickListener{
            val a=Intent(this, ViewAttendance::class.java)
            a.putExtra("email",email)
            startActivity(a)
        }

        grades.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, ViewMarks::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
            },2000)
        }

        fee.setOnClickListener {
            val b=Intent(this, View_Fee::class.java)
            b.putExtra("email",email)
            startActivity(b)
        }

        behaviour.setOnClickListener {
            val c=Intent(this, View_Behaviour::class.java)
            c.putExtra("email",email)
            startActivity(c)
        }

        alerts.setOnClickListener {
            val d= Intent(this, View_Alerts::class.java)
            d.putExtra("email",email)
            startActivity(d)
        }

        progress.setOnClickListener {
            val e=Intent(this, View_Progress::class.java)
            e.putExtra("email",email)
            startActivity(e)
        }

        changepassword.setOnClickListener{
            val f=Intent(this, Change_Student_Password::class.java)
            f.putExtra("email", email)
            startActivity(f)
        }

        logout.setOnClickListener{
            val i=Intent(this,Login_Page::class.java)
            startActivity(i)
            finish()
            showCustomToast("LOGGED OUT SUCCESSFULLY")
        }
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