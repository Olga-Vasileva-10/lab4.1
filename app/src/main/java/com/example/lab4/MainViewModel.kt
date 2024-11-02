package com.example.lab4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Question(val text: String, val answer: Boolean, val Cheat: String) // Добавляем подсказку

class MainViewModel : ViewModel() {
    private val questionBank = listOf(
        Question("Лондон — столица Великобритании?", true, "Это столица Англии."),
        Question("Флаг Италии имеет красный, белый и желтый цвета?", false, "В флаге Италии есть только три цвета."),
        Question("Солнце — это звезда.", true, "Это не планета, а звезда."),
        Question("Гарри Поттер — это книга о супергерое?", false, "Это книга о магии и приключениях.")
    )

    private val _currentQuestionIndex = MutableLiveData(0)
    val currentQuestionIndex: LiveData<Int> get() = _currentQuestionIndex

    private val _correctAnswersCount = MutableLiveData(0)
    val correctAnswersCount: LiveData<Int> get() = _correctAnswersCount

    fun nextQuestion() {
        if (_currentQuestionIndex.value != null && _currentQuestionIndex.value!! < questionBank.size - 1) {
            _currentQuestionIndex.value = _currentQuestionIndex.value!! + 1
        }
    }

    fun checkAnswer(answer: Boolean): Boolean {
        return questionBank[_currentQuestionIndex.value ?: 0].answer == answer
    }

    fun increaseCorrectAnswersCount() {
        _correctAnswersCount.value = (_correctAnswersCount.value ?: 0) + 1
    }

    fun getTotalQuestions() = questionBank.size
    fun getCurrentQuestion() = questionBank[_currentQuestionIndex.value ?: 0].text
    fun getCurrentCheat() = questionBank[_currentQuestionIndex.value ?: 0].Cheat // Новый метод для получения подсказки
}
