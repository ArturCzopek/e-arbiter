package pl.cyganki.tournament.model.dto

import pl.cyganki.utils.model.TaskUserDetails

data class TaskPreview(
        val name: String,
        val description: String,
        val maxPoints: Int,
        val taskUserDetails: TaskUserDetails?
)