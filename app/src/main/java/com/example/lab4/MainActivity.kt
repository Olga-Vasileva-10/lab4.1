package com.example.lab4

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View

data class Question(val text: String, val answer: Boolean)

class MainActivity : AppCompatActivity() {
    private val questionBank = listOf(
        Question("Лондон — столица Великобритании?", true),
        Question("Флаг Италии имеет красный, белый и желтый цвета?", false),
        Question("Солнце — это звезда.", true),
        Question("Гарри Поттер — это книга о супергерое?", false)
    )

    private val TOTAL_QUESTIONS = questionBank.size
    private var currentQuestionIndex: Int = 0
    private var correctAnswersCount: Int = 0
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Инициализация кнопок и TextView
        trueButton = findViewById(R.id.True)
        falseButton = findViewById(R.id.False)
        nextButton = findViewById(R.id.Next)
        questionTextView = findViewById(R.id.textView)

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex", 0)
            correctAnswersCount = savedInstanceState.getInt("correctAnswersCount", 0)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        trueButton.setOnClickListener { onAnswerSelected(true) }
        falseButton.setOnClickListener { onAnswerSelected(false) }

        // Обработчик для кнопки "Next"
        nextButton.setOnClickListener {
            currentQuestionIndex++
            updateQuestion() // Обновление вопроса
        }

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentQuestionIndex", currentQuestionIndex)
        outState.putInt("correctAnswersCount", correctAnswersCount)
    }

    private fun onAnswerSelected(answer: Boolean) {
        val isCorrect = checkAnswer(answer)
        if (isCorrect) {
            correctAnswersCount++
        }

        // Скрываем кнопки "True" и "False"
        trueButton.visibility = View.INVISIBLE
        falseButton.visibility = View.INVISIBLE

        // Скрываем кнопку "Next", если это последний вопрос
        if (currentQuestionIndex == TOTAL_QUESTIONS - 1) {
            showResult()
            nextButton.visibility = View.INVISIBLE
        } else {
            nextButton.visibility = View.VISIBLE
        }
    }

    private fun showResult() {
        Toast.makeText(this, "Правильные ответы: $correctAnswersCount из $TOTAL_QUESTIONS", Toast.LENGTH_LONG).show()
    }

    private fun checkAnswer(answer: Boolean): Boolean {
        return questionBank[currentQuestionIndex].answer == answer
    }

    private fun updateQuestion() {
        if (currentQuestionIndex < TOTAL_QUESTIONS) {
            val question = questionBank[currentQuestionIndex]
            questionTextView.text = question.text // Устанавливаем текст вопроса в TextView

            // Показываем кнопки "True" и "False" при обновлении вопроса
            trueButton.visibility = View.VISIBLE
            falseButton.visibility = View.VISIBLE
        }
    }
}
