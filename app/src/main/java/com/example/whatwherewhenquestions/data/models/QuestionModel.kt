package com.example.whatwherewhenquestions.data.models

data class QuestionModel(
    var question: String,
    var answer: String,
    var comment: String? = null,
    var source: String? = null
)
