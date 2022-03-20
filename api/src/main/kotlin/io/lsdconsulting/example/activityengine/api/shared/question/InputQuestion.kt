package io.lsdconsulting.example.activityengine.api.shared.question

data class InputQuestion(
    val id: String,
    val displayOrder: Int,
    val question: String,
    val standardAnswer: String,
    val maxScore: Int
)
