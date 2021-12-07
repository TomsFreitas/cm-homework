package com.example.dialer

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.lang.StringIndexOutOfBoundsException
import com.example.dialer.SpeedDialActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textbox: TextView = findViewById(R.id.textView)

        val buttons = ArrayList<Button>()
        for (i in 0..11){
            val id = "button$i"
            val buttonid = resources.getIdentifier(id, "id", packageName)
            buttons.add(findViewById(buttonid))
            buttons[i].setOnClickListener {
                if (i != 10 && i != 11) {
                    textbox.text = textbox.text.toString().plus(i.toString())
                }else{
                    if (i == 10){
                        textbox.text = textbox.text.toString().plus("*")
                    }else{
                        textbox.text = textbox.text.toString().plus("#")

                    }
                }
            }
        }

        val buttondelete: ImageButton = findViewById(R.id.imageButton2)
        buttondelete.setOnClickListener {
            try {
                textbox.text =
                    textbox.text.toString().subSequence(0, textbox.text.toString().length - 1)
            }catch (e: StringIndexOutOfBoundsException){
                Log.e("Delete", "Nothing to delete")
            }
        }

        val buttoncall: ImageButton = findViewById(R.id.imageButton)
        buttoncall.setOnClickListener {
            val dial = Intent(Intent.ACTION_DIAL)
            dial.data = Uri.parse("tel:" + textbox.text.toString())
            startActivity(dial)
            Log.d("Dial", "Dialing")
        }

        for (i in 1..3){
            val sharedPreferences = applicationContext.getSharedPreferences("Phones", Context.MODE_PRIVATE)
            val id = "speeddial$i"
            val buttonid: Button = findViewById(resources.getIdentifier(id, "id", packageName))
            val name = sharedPreferences.getString(id, "None").toString()
            Log.d("shared", name)
            if (name != "None") {
                buttonid.setText(name.split(",")[0])
            }else{
                buttonid.setText("None")
            }
            buttonid.setOnLongClickListener{
                val speeddial = Intent(this, SpeedDialActivity::class.java )
                speeddial.putExtra("id", id)
                startActivity(speeddial)
                true
            }
            buttonid.setOnClickListener{
                if (name != "None"){
                    textbox.setText(name.split(",")[1])

                }else{
                    val toast = Toast.makeText(applicationContext, "No Contact Saved", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val textbox: TextView = findViewById(R.id.textView)
        for (i in 1..3){
            val sharedPreferences = applicationContext.getSharedPreferences("Phones", Context.MODE_PRIVATE)
            val id = "speeddial$i"
            val buttonid: Button = findViewById(resources.getIdentifier(id, "id", packageName))
            val name = sharedPreferences.getString(id, "None").toString()
            Log.d("shared", name)
            if (name != "None") {
                buttonid.setText(name.split(",")[0])
            }else{
                buttonid.setText("None")
            }
            buttonid.setOnClickListener{
                if (name != "None"){
                    textbox.setText(name.split(",")[1])

                }else{
                    val toast = Toast.makeText(applicationContext, "No Contact Saved", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
        }
    }


}