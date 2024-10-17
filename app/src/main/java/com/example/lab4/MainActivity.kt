package com.example.lab4

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.Button // Импортируем класс Button

class MainActivity : AppCompatActivity() {
    private var currentQuestionIndex: Int = 0 // Индекс текущего вопроса
    private lateinit var trueButton: Button // Кнопка "True"
    private lateinit var falseButton: Button // Кнопка "False"
    private lateinit var nextButton: Button // Кнопка "Next"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.True)
        falseButton = findViewById(R.id.False)
        nextButton = findViewById(R.id.Next)

        // Восстановление состояния, если доступен savedInstanceState
        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex", 0)
            // Здесь восстановите вопрос, используя currentQuestionIndex
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Установка обработчиков событий для кнопок
        trueButton.setOnClickListener { onAnswerSelected(true) }
        falseButton.setOnClickListener { onAnswerSelected(false) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Сохранение текущего вопроса
        outState.putInt("currentQuestionIndex", currentQuestionIndex)
    }

    private fun onAnswerSelected(answer: Boolean) {
        // Скрываем кнопки "True" и "False"
        trueButton.visibility = View.INVISIBLE
        falseButton.visibility = View.INVISIBLE

    }

    companion object {
        private const val TOTAL_QUESTIONS = 10 // Общее количество вопросов
    }
}
