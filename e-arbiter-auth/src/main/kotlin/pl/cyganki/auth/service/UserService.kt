package pl.cyganki.auth.service

import org.springframework.stereotype.Service
import pl.cyganki.auth.repository.UserRepository
import pl.cyganki.utils.model.UserNameEmail

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUserNameById(id: Long) = userRepository.findUserNameById(id)

    fun getUserNameAndEmailById(id: Long) = userRepository.findOne(id).run{ UserNameEmail(name, email) }

    fun getAllUserNamesAndEmails() = userRepository.findAll().map { UserNameEmail(it.name, it.email) }
}