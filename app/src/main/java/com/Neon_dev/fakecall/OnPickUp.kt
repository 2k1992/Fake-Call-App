package com.Neon_dev.fakecall

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.TextView

class OnPickUp : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_pick_up)

        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )


        val messageNameDisplay = intent.getStringExtra("nameInputs")
        val messageTextNames: TextView = findViewById(R.id.tvCallerNameDisplays)

        if (TextUtils.isEmpty(messageNameDisplay.toString())){
            messageTextNames.text = ("Visa Agency")
        }else
            messageTextNames.text = messageNameDisplay


        val messagePhoneDisplay = intent.getStringExtra("phoneInputs")
        val messageTextPhones: TextView = findViewById(R.id.tvCallerNumberDisplays)

        if (TextUtils.isEmpty(messageNameDisplay.toString())){
            messageTextPhones.text = ("+44 204 577 00 77")
        }else
            messageTextPhones.text = messagePhoneDisplay

        val btnDecline = findViewById<ImageButton>(R.id.btn_decline)
        btnDecline.setOnClickListener{
            finish()
        }

        val simpleChronometer = findViewById<View>(R.id.tvTimeCounter) as Chronometer
        simpleChronometer.start()
    }
}