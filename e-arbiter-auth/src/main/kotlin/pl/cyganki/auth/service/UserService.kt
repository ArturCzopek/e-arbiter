package pl.cyganki.auth.service

import org.springframework.stereotype.Service
import pl.cyganki.auth.database.entity.DbUser
import pl.cyganki.auth.database.repository.RoleRepository
import pl.cyganki.auth.database.repository.UserRepository
import pl.cyganki.auth.exception.WrongGithubUserException
import pl.cyganki.utils.security.User

@Service
class UserService(
        val userRepository: UserRepository,
        val roleRepository: RoleRepository
) {

    val DEFAULT_USER_ROLE_NAME = "USER"

    fun getLoggedInUser(userMap: Map<String, Any>): User {

        val githubId = (userMap["id"] as Int).toLong()
        var dbUser = userRepository.findOneByGithubId(githubId)

        if (dbUser == null) {
            val githubLogin = userMap["login"] as String

            if (githubLogin.isEmpty()) {
                throw WrongGithubUserException()
            }

            dbUser = createUser(githubId, githubLogin)
        }

        val user = User(id = dbUser.id, name = dbUser.name)

        return user
    }

    fun getAllUsersFromDb() = userRepository.findAll()

    private fun createUser(githubId: Long, githubLogin: String): DbUser {
        val defaultRole = roleRepository.findOneByName(DEFAULT_USER_ROLE_NAME)
        val newUser = DbUser(githubId = githubId, name = githubLogin, roles = listOf(defaultRole))
        userRepository.save(newUser)
        return newUser
    }
}