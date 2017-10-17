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
}