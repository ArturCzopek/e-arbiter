package pl.cyganki.results.exception

class NotFoundResultException(userId: Long, taskId: String): RuntimeException("Cannot find results for user with id $userId and task with id $taskId")