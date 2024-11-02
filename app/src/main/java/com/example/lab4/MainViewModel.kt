package com.example.lab4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Question(val text: String, val answer: Boolean, val Cheat: String) // Добавляем подсказку

class MainViewModel : ViewModel() {
    private val remainingCheat = MutableLiveData(3)
    val remainingCheatt: LiveData<Int> get() = remainingCheat
    private val questionBank = listOf(
        Question("Лондон — столица Великобритании?", true, "True"),
        Question("Флаг Италии имеет красный, белый и желтый цвета?", false, "False"),
        Question("Солнце — это звезда.", true, "True"),
        Question("Гарри Поттер — это книга о супергерое?", false, "False")
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
    fun useCheat(): Boolean {
        return if (remainingCheat.value ?: 0 > 0) {
            remainingCheat.value = (remainingCheat.value ?: 0) - 1
            true
        } else {
            false
        }
    }
    fun getTotalQuestions() = questionBank.size
    fun getCurrentQuestion() = questionBank[_currentQuestionIndex.value ?: 0].text
    fun getCurrentCheat() = questionBank[_currentQuestionIndex.value ?: 0].Cheat // Новый метод для получения подсказки
}
