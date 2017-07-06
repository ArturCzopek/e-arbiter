package pl.cyganki.auth.database.entity

import javax.persistence.*

@Entity
@Table(name = "Roles")
data class Role(
        @Id @GeneratedValue @Column(name = "role_id") var id: Long = 0,
        @Column(name = "role_name") var name: String = ""
)
