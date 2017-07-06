package pl.cyganki.auth.database.repository

import org.springframework.data.repository.CrudRepository
import pl.cyganki.auth.database.entity.User

interface UserRepository: CrudRepository<User, Long> {

    fun findOneByGithubId(githubId: Long): User?

    override fun findAll(): List<User>
}