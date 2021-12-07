package com.example.dialer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SpeedDialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed_dial)
        val id = intent.getStringExtra("id").toString()
        val name: TextView = findViewById(R.id.editTextTextPersonName)
        val phone: TextView = findViewById(R.id.editTextPhone)

        val sharedPreferences = applicationContext.getSharedPreferences("Phones", Context.MODE_PRIVATE)
        val info = sharedPreferences.getString(id, "None").toString()
        if (info != "None"){
            name.setText(info.split(",")[0])
            phone.setText(info.split(",")[1])
        }

        val savebutton: Button = findViewById(R.id.button)
        savebutton.setOnClickListener{
            sharedPreferences.edit().putString(id, name.text.toString() + "," + phone.text.toString()).commit()
            finish()
        }


    }

}