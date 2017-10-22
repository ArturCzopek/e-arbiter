package pl.cyganki.auth.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import pl.cyganki.auth.model.DbUser

interface UserRepository: CrudRepository<DbUser, Long> {

    override fun findAll(): List<DbUser>

    fun findOneByGithubId(githubId: Long): DbUser?

    @Query("select u.name from DbUser u where u.id = :id")
    fun findUserNameById(@Param("id") id: Long): String

    @Query("select u.name from DbUser u")
    fun findAllUserNames(): List<String>

    @Query("select u.email from DbUser u where u.id = :id")
    fun findUserEmailById(@Param("id") id: Long): String

    @Query("select u.email from DbUser u")
    fun findAllUsersEmails(): List<String>
}