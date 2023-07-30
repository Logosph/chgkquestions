package com.example.whatwherewhenquestions.data.models

import java.util.Calendar

// TODO: replace this model from data to ???
data class QuestionRequestParamsModel(
    val from: Calendar? = null,
    val to: Calendar? = null,
    val types: String? = null,
    val complexity: Int? = null,
    val limit: Int? = null
)
