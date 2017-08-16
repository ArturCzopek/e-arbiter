package pl.cyganki.auth.repository

import org.springframework.data.repository.CrudRepository
import pl.cyganki.auth.model.DbRole

interface RoleRepository: CrudRepository<DbRole, Long> {

    fun findOneByName(name: String): DbRole
}