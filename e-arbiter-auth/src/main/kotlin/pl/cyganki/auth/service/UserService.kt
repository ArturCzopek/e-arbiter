package pl.cyganki.auth.service

import org.springframework.stereotype.Service
import pl.cyganki.auth.repository.UserRepository

@Service
class UserService(val userRepository: UserRepository) {

    fun getUserNameById(id: Long) = userRepository.findUserNameById(id)
}