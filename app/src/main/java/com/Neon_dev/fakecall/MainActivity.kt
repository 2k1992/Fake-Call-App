package com.Neon_dev.fakecall

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.textfield.TextInputEditText


/**
 *
 * TODO: STOP "TIME" REFRESH
 * TODO: RINGTONE ................................
 * TODO: ADD VOICE ON PICKUP
 * TODO: DEBUG TEXTBOXS on enter
 *
 *
 *
 */
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var btnSend : Button
    private lateinit var ringtoneCv : CardView
    private lateinit var txtViewRT : TextView
    private lateinit var txtViewT : TextView

    // edit text for alertbox
    @SuppressLint("SetTextI18n")
    fun withEditText(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Timer")
        val dialogLayout = inflater.inflate(R.layout.activity_onclick_timer, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.editText)

        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { _, _ ->
            Toast.makeText(applicationContext, editText.text.toString()+" Seconds",
                Toast.LENGTH_LONG).show()

            val timerText = editText.text.toString()
            txtViewT = findViewById(R.id.tvTimerSec)
            txtViewT.text = "$timerText Seconds"


            //this is what refreshes the screen after selecting time
            val value = editText.text.toString().trim()
            val sharedPref = getSharedPreferences("myKey", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("value", value)
            editor.apply()


        }
        builder.show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btnSend = findViewById(R.id.btnDone)
        ringtoneCv = findViewById(R.id.cvRingtone)



        val sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE)
        val value = sharedPreferences.getString("value", null)


        ringtoneCv.setOnClickListener {
            val currentRingtone: Uri = RingtoneManager.getActualDefaultRingtoneUri(
                this,
                RingtoneManager.TYPE_ALARM
            )
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
                putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE)
                putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone")
                putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentRingtone)
                putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
                putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
            }
            startActivityForResult(intent, 999)
        }

        btnSend.setOnClickListener {


            if (value != null) {
                Toast.makeText(applicationContext, "Now we wait", Toast.LENGTH_LONG).show()
                Handler().postDelayed({
                    done()
                    Toast.makeText(applicationContext, "$value Seconds", Toast.LENGTH_LONG).show()
                }, value.toLong() * 1000)
            }  else
                Toast.makeText(
                    applicationContext,
                    "select time ",
                    Toast.LENGTH_LONG
                ).show()
/**  Handler().postDelayed({
                    done()
                    Toast.makeText(applicationContext, "$value Seconds", Toast.LENGTH_LONG).show()
                }, 5000)*/
        }



    }

    @SuppressLint("SetTextI18n")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 999 && resultCode == Activity.RESULT_OK) {
            val uri = data!!.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            if (uri != null) {
                txtViewRT = findViewById(R.id.tvRingtoneName)
                txtViewRT.text = "From :" + uri.path
            }
        }
    }

    private fun done() {
        val callerName = findViewById<TextInputEditText>(R.id.tvCallerName)
        val callerPhone = findViewById<TextInputEditText>(R.id.tvCallerNumber)

        val messageName = callerName.text.toString()
        val messageNumber = callerPhone.text.toString()



        val i = Intent(this, CallScreenActivity::class.java).apply {

            putExtra("nameInput", messageName)
            putExtra("phoneInput", messageNumber)

        }
        startActivity(i)
    }





}