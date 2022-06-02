package com.example.projektarbete1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FinishScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_screen)

        val textViewScore = findViewById<TextView>(R.id.textViewScore)
        val startOverBtn = findViewById<Button>(R.id.startOverBtn)

        val extras = intent.extras
        if (extras != null) {
            val score = extras.getInt("score")
            textViewScore.text = score.toString()
        }

        startOverBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            //val score = null
        }

    }
}