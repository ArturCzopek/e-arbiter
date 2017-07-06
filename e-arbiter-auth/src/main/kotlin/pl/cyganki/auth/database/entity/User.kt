package pl.cyganki.auth.database.entity

import javax.persistence.*

@Entity
@Table(name = "Users")
data class User(
        @Id
        @GeneratedValue
        @Column(name = "user_id")
        var id: Long = 0,

        @Column(name = "github_id")
        var githubId: Long = 0,

        // gotten from GitHub auth
        @Column(name = "user_name")
        var name: String = "",

        // is user enabled, by default - yes
        @Column(name = "enabled")
        var enabled: Boolean = true,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "Users_roles",
                joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "user_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "role_id", referencedColumnName = "role_id")))
        var roles: List<Role> = emptyList()
)