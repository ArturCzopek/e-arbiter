package pl.cyganki.auth.service

import org.springframework.stereotype.Service
import pl.cyganki.auth.model.DbUser
import pl.cyganki.auth.repository.RoleRepository
import pl.cyganki.auth.repository.UserRepository
import pl.cyganki.utils.GlobalValues
import pl.cyganki.utils.exception.WrongGithubUserException
import pl.cyganki.utils.security.dto.Role
import pl.cyganki.utils.security.dto.User

typealias GitHubUserMap = Map<String, Any>

@Service
class AuthService(
        val userRepository: UserRepository,
        val roleRepository: RoleRepository
) {

    fun getLoggedInUser(userMap: GitHubUserMap): User {

        val githubId = (userMap[GlobalValues.GH_ID] as Int).toLong()
        var dbUser = userRepository.findOneByGithubId(githubId)

        if (dbUser == null) {
            val githubLogin = (userMap[GlobalValues.GH_LOGIN] ?: "") as String

            if (githubLogin.isEmpty()) {
                throw WrongGithubUserException()
            }

            dbUser = createUser(githubId, githubLogin)
        }

        var roles: List<Role> = emptyList()

        dbUser.roles.forEach { (id, name) -> roles += Role(id, name) }

        return User(id = dbUser.id, name = dbUser.name, roles = roles)
    }

    fun getAllUsersFromDb() = userRepository.findAll()

    private fun createUser(githubId: Long, githubLogin: String): DbUser {
        val defaultRole = roleRepository.findOneByName(GlobalValues.DEFAULT_ROLE_NAME)
        val newUser = DbUser(githubId = githubId, name = githubLogin, roles = listOf(defaultRole))
        userRepository.save(newUser)
        return newUser
    }
}