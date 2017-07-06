package pl.cyganki.auth.database.repository

import org.springframework.data.repository.CrudRepository
import pl.cyganki.auth.database.entity.Role


interface RoleRepository: CrudRepository<Role, Long> {

    fun findOneByName(name: String): Role
}