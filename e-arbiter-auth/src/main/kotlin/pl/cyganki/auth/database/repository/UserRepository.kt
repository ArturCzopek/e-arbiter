package pl.cyganki.auth.database.repository

import org.springframework.data.repository.CrudRepository
import pl.cyganki.auth.database.entity.DbUser

interface UserRepository: CrudRepository<DbUser, Long> {

    fun findOneByGithubId(githubId: Long): DbUser?

    override fun findAll(): List<DbUser>
}