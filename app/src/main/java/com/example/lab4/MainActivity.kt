package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        trueButton = findViewById(R.id.True)
        falseButton = findViewById(R.id.False)
        nextButton = findViewById(R.id.Next)
        cheatButton = findViewById(R.id.Cheat)
        questionTextView = findViewById(R.id.textView)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        trueButton.setOnClickListener { onAnswerSelected(true) }
        falseButton.setOnClickListener { onAnswerSelected(false) }
        nextButton.setOnClickListener {
            viewModel.nextQuestion()
            updateQuestion()
        }

        cheatButton.setOnClickListener { startCheatActivity() }

        viewModel.currentQuestionIndex.observe(this) {
            updateQuestion()
        }

        viewModel.correctAnswersCount.observe(this) { count ->
            nextButton.visibility = if (viewModel.currentQuestionIndex.value == viewModel.getTotalQuestions() - 1) {
                showResult()
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }

        updateQuestion()
    }

    private fun onAnswerSelected(answer: Boolean) {
        if (viewModel.checkAnswer(answer)) {
            viewModel.increaseCorrectAnswersCount()
        }
        trueButton.visibility = View.INVISIBLE
        falseButton.visibility = View.INVISIBLE
    }

    private fun showResult() {
        Toast.makeText(this, "Правильные ответы: ${viewModel.correctAnswersCount.value} из ${viewModel.getTotalQuestions()}",
            Toast.LENGTH_LONG).show()
    }

    private fun updateQuestion() {
        questionTextView.text = viewModel.getCurrentQuestion()
        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE
    }

    private fun startCheatActivity() {
        if (viewModel.useCheat()) {
            val cheatText = viewModel.getCurrentCheat()
            val intent = Intent(this, CheatActivity::class.java).apply {
                putExtra(CheatActivity.EXTRA_CHEAT_TEXT, cheatText)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Подсказки закончились!", Toast.LENGTH_LONG).show()
        }
    }
}
