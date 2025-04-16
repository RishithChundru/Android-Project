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

class Check_Risk : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_risk)
        db = AppDatabase.getDatabase(this)
        dao = db.userDao()
        val email=findViewById<EditText>(R.id.studentemail)
        val checkRisk_button=findViewById<Button>(R.id.checkrisk)
        val risk_status=findViewById<TextView>(R.id.riskText)
        val sendAlert_button=findViewById<Button>(R.id.sendAlertButton)

        val toolbar = findViewById<Toolbar>(R.id.toolbar11)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val homeIcon= findViewById<ImageView>(R.id.homeIcon11)
        val logoutIcon= findViewById<ImageView>(R.id.logout14)
        homeIcon.setOnClickListener {
            val i=Intent(this, Teacher_Dashboard::class.java)
            startActivity(i)
        }
        logoutIcon.setOnClickListener {
            val j=Intent(this, Login_Page::class.java)
            startActivity(j)
        }

        checkRisk_button.setOnClickListener {
            val Email=email.text.toString().trim()
            if (Email.isEmpty()) {
                showCustomToast("Please enter student email")
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val user=dao.getUserByEmail(Email)
                if(user==null){
                    runOnUiThread {
                        showCustomToast("User Not Found")
                        risk_status.text="No data Found"
                    }
                }
                else{
                    runOnUiThread {
                        if((user.percentage?:100)<40 || (user.attendance?:100)<75 ||
                            (user.balanceFee?:0)>20000 || (user.behaviour?:"GOOD").uppercase()=="BAD"){
                            risk_status.text = "STUDENT IS AT RISK OF DROPOUT"
                            sendAlert_button.visibility = View.VISIBLE
                        }
                        else{
                            risk_status.text="STUDENT IS NOT AT RISK OF DROPOUT"
                            sendAlert_button.visibility=View.GONE
                        }
                    }
                    sendAlert_button.setOnClickListener {
                        val alertMsg = "Please maintain your attendance, grades, behaviour, and pay fees on time. Otherwise, you may be dropped out from our school."
                        lifecycleScope.launch {
                            dao.updateAlertMessage(Email, alertMsg)
                            runOnUiThread {
                                showCustomToast("Alert Sent to Student!")
                            }
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
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 350)
        toast.duration = Toast.LENGTH_SHORT
        toast.setView(layout)
        toast.show()
    }
}