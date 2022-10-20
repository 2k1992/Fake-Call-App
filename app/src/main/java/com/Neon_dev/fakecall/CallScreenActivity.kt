package com.Neon_dev.fakecall

import android.annotation.SuppressLint
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess


class CallScreenActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_screen)


/**
        val ringtoneDisplay : String? = intent.getStringExtra ("ring")
        val fileRingtone : Uri = Uri.parse(ringtoneDisplay)
        val ringtoneSound : Ringtone = RingtoneManager.getRingtone(this , fileRingtone)

        ringtoneSound.play()
 */
        val notification: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL)
        val ringtone = RingtoneManager.getRingtone(applicationContext, notification)
        ringtone.play()



        //Force wake screen
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                 WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                 WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                 WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )

        val messageNameDisplay = intent.getStringExtra("nameInput")
        val messageTextName: TextView = findViewById(R.id.tvCallerNameDisplay)

        if (isEmpty(messageNameDisplay.toString())){
            messageTextName.text = ("Visa Agency")
        }else
            messageTextName.text = messageNameDisplay


        val messagePhoneDisplay = intent.getStringExtra("phoneInput")
        val messageTextPhone: TextView = findViewById(R.id.tvCallerNumberDisplay)

        if (isEmpty(messagePhoneDisplay.toString())){
            messageTextPhone.text  = ("+44 204 577 00 77")
        }else
            messageTextPhone.text = messagePhoneDisplay

        //send name and number to onpickup activity
        val btnAccept = findViewById<ImageButton>(R.id.btn_accept)
        btnAccept.setOnClickListener{
            val i = Intent(this, OnPickUp::class.java).apply {
                putExtra("nameInputs", messageNameDisplay)
                putExtra("phoneInputs", messagePhoneDisplay)
            }
            startActivity(i)
            ringtone.stop()
        }

        val btnDecline = findViewById<ImageButton>(R.id.btn_decline)
        btnDecline.setOnClickListener{
            finish()
            ringtone.stop()

        }
    }


}