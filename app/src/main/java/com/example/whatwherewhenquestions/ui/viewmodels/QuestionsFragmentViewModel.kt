package com.example.whatwherewhenquestions.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatwherewhenquestions.data.models.QuestionModel
import com.example.whatwherewhenquestions.data.models.QuestionRequestParamsModel
import com.example.whatwherewhenquestions.data.repository.QuestionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionsFragmentViewModel : ViewModel() {

    private val questionsRepository = QuestionsRepository()
    val questions: MutableLiveData<ArrayList<QuestionModel>?> = MutableLiveData(null)

    var expandedFilter = false
    var types = Array<Boolean>(6) {false}
    var complexity: Int = 0
    var limit: Int = 0
        set(value) {
            if (value !in 1..100){
                field = 20
            } else {
                field = value
            }
        }

    fun getQuestions(lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch(Dispatchers.IO) {
            var typesString = ""
            for ((k, el) in types.withIndex()) {
                if (el) {
                    typesString += (k + 1)
                }
            }

            Log.d("MyLog", "Complexity: $complexity, limit: $limit, types: $typesString")
            val params = QuestionRequestParamsModel(
                complexity = if (complexity != 0) complexity else null,
                limit = limit,
                types = typesString
            )
            questionsRepository.getQuestionsList(params)
        }
        questionsRepository.downloadedQuestions.observe(lifecycleOwner) {
            questions.value = it
        }
    }
}