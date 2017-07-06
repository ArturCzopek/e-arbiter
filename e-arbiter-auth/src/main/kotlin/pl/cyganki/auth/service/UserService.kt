package pl.cyganki.auth.service

import org.springframework.stereotype.Service
import pl.cyganki.auth.database.entity.User
import pl.cyganki.auth.database.repository.RoleRepository
import pl.cyganki.auth.database.repository.UserRepository
import pl.cyganki.auth.exception.WrongGithubUserException

@Service
class UserService(
        val userRepository: UserRepository,
        val roleRepository: RoleRepository
) {

    val DEFAULT_USER_ROLE_NAME = "USER"

    fun getLoggedInUser(userMap: Map<String, Any>): User {

        val githubId = (userMap["id"] as Int).toLong()
        var user = userRepository.findOneByGithubId(githubId)

        if (user == null) {
            val githubLogin = userMap["login"] as String

            if (githubLogin.isEmpty()) {
                throw WrongGithubUserException()
            }

            user = createUser(githubId, githubLogin)
        }

        return user
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    private fun createUser(githubId: Long, githubLogin: String): User {
        val defaultRole = roleRepository.findOneByName(DEFAULT_USER_ROLE_NAME)
        val newUser = User(githubId = githubId, name = githubLogin, roles = listOf(defaultRole))
        userRepository.save(newUser)
        return newUser
    }
}