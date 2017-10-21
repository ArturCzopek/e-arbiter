package pl.cyganki.auth.service

import org.springframework.stereotype.Service
import pl.cyganki.auth.repository.UserRepository

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUserNameById(id: Long) = userRepository.findUserNameById(id)

    fun getAllUserNames() = userRepository.findAllUserNames()

    fun getUserEmailById(id: Long) = userRepository.findUserEmailById(id)

    fun getAllUsersEmails() = userRepository.findAllUsersEmails()

}