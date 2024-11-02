package com.example.lab4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Question(val text: String, val answer: Boolean)

class MainViewModel : ViewModel() {
    private val questionBank = listOf(
        Question("Лондон — столица Великобритании?", true),
        Question("Флаг Италии имеет красный, белый и желтый цвета?", false),
        Question("Солнце — это звезда.", true),
        Question("Гарри Поттер — это книга о супергерое?", false)
    )

    private val _currentQuestionIndex = MutableLiveData(0)
    val currentQuestionIndex: LiveData<Int> get() = _currentQuestionIndex

    private val _correctAnswersCount = MutableLiveData(0)
    val correctAnswersCount: LiveData<Int> get() = _correctAnswersCount

    fun nextQuestion() {
        _currentQuestionIndex.value = (_currentQuestionIndex.value ?: 0) + 1
    }

    fun checkAnswer(answer: Boolean): Boolean {
        return questionBank[_currentQuestionIndex.value ?: 0].answer == answer
    }

    fun increaseCorrectAnswersCount() {
        _correctAnswersCount.value = (_correctAnswersCount.value ?: 0) + 1
    }

    fun getTotalQuestions() = questionBank.size
    fun getCurrentQuestion() = questionBank[_currentQuestionIndex.value ?: 0].text
}
