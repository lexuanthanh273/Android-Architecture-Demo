package com.architecture.crash

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.architecture.crash.databinding.ActivityCrashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CrashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val error: String? = intent.getStringExtra("Error")
        val software: String? = intent.getStringExtra("Software")
        val date: String? = intent.getStringExtra("Date")

        binding.txtCrash.text = error.plus(software).plus(date)

        binding.btnGoBack.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData =
                ClipData.newPlainText(
                    "crash",
                    error.plus(software).plus(date)
                )
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(
                this,
                "Copy",
                Toast.LENGTH_SHORT
            ).show()
            this.finishAffinity()
        }
    }


}