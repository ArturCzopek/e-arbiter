package pl.cyganki.auth.service

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import pl.cyganki.auth.EArbiterAuthApplication
import pl.cyganki.auth.database.repository.UserRepository
import pl.cyganki.utils.GlobalValues
import pl.cyganki.utils.exception.WrongGithubUserException
import pl.cyganki.utils.security.dto.Role
import pl.cyganki.utils.security.dto.User

/**
 * Used data to tests are defined in proper liquibase XML
 * @see /resources/db/changelog/test-data/db.changelog-test-data.xml
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(EArbiterAuthApplication::class), loader = SpringBootContextLoader::class)
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    var initUsersSize = 0L

    @Before
    fun `set init user size`() {
        initUsersSize = userRepository.count()
    }

    @Test
    fun `should return existing user by GitHub Id`() {
        // given
        val gitHubUserMap = mapOf(GlobalValues.GH_ID to 1234234)

        // when
        val user = userService.getLoggedInUser(gitHubUserMap)
        val usersInDb = userRepository.count()

        // then
        val expectedUser = User(2, "UserLol", listOf(Role(1, GlobalValues.DEFAULT_ROLE_NAME)))
        assertEquals(expectedUser, user)
        assertEquals(initUsersSize, usersInDb)
    }

    @Test
    fun `should return new user by GitHub Id and add this user to database`() {
        // given
        val gitHubUserMap = mapOf(
                GlobalValues.GH_ID to 6666666,
                GlobalValues.GH_LOGIN to "NewTestUser"
        )

        // when
        val user = userService.getLoggedInUser(gitHubUserMap)
        val usersInDb = userRepository.count()

        // then
        val expectedUser = User(initUsersSize + 1, "NewTestUser", listOf(Role(1, GlobalValues.DEFAULT_ROLE_NAME)))
        assertEquals(expectedUser, user)
        assertEquals(initUsersSize + 1, usersInDb)
    }

    @Test(expected = WrongGithubUserException::class)
    fun `should throw WrongGithubUserException for non-existing GitHub user`() {
        // given
        val gitHubUserMap = mapOf(GlobalValues.GH_ID to 66666667)   // there will be new user but we don't have a name

        // when
        userService.getLoggedInUser(gitHubUserMap) // WrongGithubUserException::class should be thrown there
        val usersInDb = userRepository.count()

        // then
        // exception expected, no new user
        assertEquals(initUsersSize, usersInDb)
    }

    @Test
    fun `should return all users from DB`() {
        // when
        val users = userService.getAllUsersFromDb()

        // then
        assertEquals(initUsersSize.toInt(), users.size)
    }
}