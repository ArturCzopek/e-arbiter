package pl.cyganki.auth.model

import javax.persistence.*

@Entity
@Table(name = "Roles")
data class DbRole(
        @Id
        @GeneratedValue
        @Column(name = "role_id", unique = true, nullable = false)
        var id: Long = 0,

        @Column(name = "role_name", unique = true, nullable = false)
        var name: String = ""
)
