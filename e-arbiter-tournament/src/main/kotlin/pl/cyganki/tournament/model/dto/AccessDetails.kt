package pl.cyganki.tournament.model.dto

/**
 * DTO which defines access rights for user for proper tournament (say yes/no)
 */
data class AccessDetails(
     val publicFlag: Boolean,
     val owner: Boolean,
     val participateInTournament: Boolean,
     val resultsVisible: Boolean
)