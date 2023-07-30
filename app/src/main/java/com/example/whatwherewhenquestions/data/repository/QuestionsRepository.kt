package com.example.whatwherewhenquestions.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.whatwherewhenquestions.data.models.QuestionModel
import com.example.whatwherewhenquestions.data.models.QuestionRequestParamsModel
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.w3c.dom.Element
import java.text.SimpleDateFormat
import java.util.Locale

class QuestionsRepository {

    private val BASE_URL = "https://db.chgk.info/random"
    val downloadedQuestions: MutableLiveData<ArrayList<QuestionModel>?> = MutableLiveData(null)

    fun getQuestionsList(params: QuestionRequestParamsModel) {

        var request = ""
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val res = ArrayList<QuestionModel>()

        // Building request
        request += if (params.from == null) "" else "/from_${sdf.format(params.from)}"
        request += if (params.to == null) "" else "/to_${sdf.format(params.to)}"
        request += if (params.types == null) "" else "/types${params.types}"
        request += if (params.complexity == null) "" else "/complexity${params.complexity}"
        request += if (params.limit == null) "/limit20" else "/limit${params.limit}"
        try {
            val doc = Jsoup.connect(BASE_URL + request).get()
            val questions: ArrayList<QuestionModel>
            val tag = doc.select("div[class=\"random_question\"]")


            var temp: QuestionModel

            for (element in tag) {
                temp = QuestionModel("", "")
                temp.question = element.wholeOwnText().trim()
                for (daughter in element.select("div[class='collapsible collapsed']")[0].select("p")) {
                    when {
                        "Ответ:" in daughter.text() -> temp.answer = daughter.wholeText().trim()
                        "Комментарий:" in daughter.text() -> temp.comment =
                            daughter.wholeText().trim()

                        "Источник(и):" in daughter.text() -> temp.source =
                            daughter.wholeText().trim()
                    }
                }

                res.add(temp)
            }
        } catch (e: Exception) {

        }

        downloadedQuestions.postValue(res)
    }
}