package pl.cyganki.auth.service

import org.springframework.stereotype.Service
import pl.cyganki.auth.model.DbUser
import pl.cyganki.auth.repository.RoleRepository
import pl.cyganki.auth.repository.UserRepository
import pl.cyganki.utils.GlobalValues
import pl.cyganki.utils.model.UserNameEmail

@Service
class UserService(
        private val userRepository: UserRepository,
        private val roleRepository: RoleRepository
) {

    fun getUserNameById(id: Long) = userRepository.findUserNameById(id)

    fun getUserNameAndEmailById(id: Long) = userRepository.findOne(id).run { UserNameEmail(name, email) }

    fun getAllUserNamesAndEmails() = userRepository.findAll().map { UserNameEmail(it.name, it.email) }

    fun toggleAdminRole(userId: Long): DbUser {
        val user = userRepository.findOne(userId)
        val adminRole = roleRepository.findOneByName(GlobalValues.ADMIN_ROLE_NAME)

        if (adminRole in user.roles) user.roles -= adminRole else user.roles += adminRole
        return userRepository.save(user)
    }

    fun toggleStatus(userId: Long): DbUser {
        val user = userRepository.findOne(userId)
        user.enabled = !user.enabled
        return userRepository.save(user)
    }
}