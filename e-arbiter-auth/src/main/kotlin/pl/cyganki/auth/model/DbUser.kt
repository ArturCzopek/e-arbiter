package pl.cyganki.auth.model

import javax.persistence.*

@Entity
@Table(name = "Users")
data class DbUser(

        @Id
        @GeneratedValue
        @Column(name = "user_id", unique = true, nullable = false)
        var id: Long = 0,

        @Column(name = "github_id", unique = true, nullable = false)
        var githubId: Long = 0,

        // gotten from GitHub auth
        @Column(name = "user_name", unique = true, nullable = false)
        var name: String = "",

        @Column(name = "email", unique = true, nullable = false)
        var email: String = "",

        // is user enabled, by default - yes
        @Column(name = "enabled")
        var enabled: Boolean = true,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "Users_roles",
                joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "user_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "role_id", referencedColumnName = "role_id"))
        )
        var roles: List<DbRole> = emptyList()
)