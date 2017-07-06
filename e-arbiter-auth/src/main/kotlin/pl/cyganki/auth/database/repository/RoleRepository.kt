package pl.cyganki.auth.database.repository

import org.springframework.data.repository.CrudRepository
import pl.cyganki.auth.database.entity.DbRole


interface RoleRepository: CrudRepository<DbRole, Long> {

    fun findOneByName(name: String): DbRole
}