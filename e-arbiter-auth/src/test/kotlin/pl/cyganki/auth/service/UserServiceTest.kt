package pl.cyganki.auth.service

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import pl.cyganki.auth.EArbiterAuthApplication

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
    private lateinit var userService: UserService

    @Test
    fun `should return a name for user with a valid id`() {
        // given
        val ids = (1L..5L)  // 1, 2, 3, 4, 5

        // when
        val foundNames = ids.map { userService.getUserNameById(it) }

        // then
        listOf("TestowyUser", "UserLol", "ArturCzopek", "KonradOnieszczuk", "Miracle")
                .forEachIndexed { index, name -> Assert.assertEquals(name, foundNames[index]) }
    }

    @Test
    fun `should return a name with email for user with a valid id`() {
        // given
        val ids = (1L..5L)  // 1, 2, 3, 4, 5

        // when
        val foundNamesEmails = ids.map { userService.getUserNameAndEmailById(it) }

        // then
        val expectedNamesEmails = mapOf(
                "TestowyUser" to "TestowyUser@TestowyUser.com",
                "UserLol" to "UserLol@UserLol.pl",
                "ArturCzopek" to "arturcz32@gmail.com",
                "KonradOnieszczuk" to "k2nder@gmail.com",
                "Miracle" to "Miracle@Miracle.com"
        )

        foundNamesEmails.forEach { Assert.assertEquals(expectedNamesEmails[it.name], it.email) }
    }

    @Test
    fun `should return names with emails for all users`() {
        // given

        // when
        val foundData = userService.getAllUserNamesAndEmails()

        // then
        foundData.apply {
            Assert.assertEquals(21, size)   // there are 21 test users
            forEach { Assert.assertTrue(it.email.contains("@")) }
        }
    }
}