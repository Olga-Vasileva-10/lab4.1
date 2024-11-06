package com.example.lab4

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CheatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val cheatTextView: TextView = findViewById(R.id.cheat_text_view)
        val apiVersionTextView: TextView = findViewById(R.id.api_version_text_view)
        val showAnswerButton: Button = findViewById(R.id.show_answer_button)

        val cheatText = intent.getStringExtra(EXTRA_CHEAT_TEXT)

        showAnswerButton.setOnClickListener {
            cheatTextView.text = cheatText
        }

        val apiVersion = "API Level: ${android.os.Build.VERSION.SDK_INT}"
        apiVersionTextView.text = apiVersion
    }

    companion object {
        const val EXTRA_CHEAT_TEXT = "com.example.lab4.cheat_text"
    }
}
