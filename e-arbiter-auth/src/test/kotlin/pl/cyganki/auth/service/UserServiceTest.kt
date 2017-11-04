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
import org.springframework.transaction.annotation.Transactional
import pl.cyganki.auth.EArbiterAuthApplication
import pl.cyganki.auth.repository.RoleRepository
import pl.cyganki.utils.GlobalValues

/**
 * Used data to tests are defined in proper liquibase XML
 * @see /resources/db/changelog/test-data/db.changelog-test-data.xml
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(EArbiterAuthApplication::class), loader = SpringBootContextLoader::class)
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var roleRepository: RoleRepository

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

    @Test
    fun `should add admin role for user without that role`() {
        // given
        val userId = TestData.normalUser
        val adminRole = roleRepository.findOneByName(GlobalValues.ADMIN_ROLE_NAME)

        // when
        val updatedUser = userService.toggleAdminRole(userId)

        // then
        updatedUser.apply {
            Assert.assertEquals(userId, id)
            Assert.assertTrue(adminRole in roles)
        }
    }

    @Test
    fun `should remove admin role for user with that role`() {
        // given
        val userId = TestData.adminUser
        val adminRole = roleRepository.findOneByName(GlobalValues.ADMIN_ROLE_NAME)

        // when
        val updatedUser = userService.toggleAdminRole(userId)

        // then
        updatedUser.apply {
            Assert.assertEquals(userId, id)
            Assert.assertFalse(adminRole in roles)
        }
    }

    @Test
    fun `should enable disabled user`() {
        // given
        val userId = TestData.disabledUser

        // when
        val updatedUser = userService.toggleStatus(userId)

        // then
        updatedUser.apply {
            Assert.assertEquals(userId, id)
            Assert.assertTrue(enabled)
        }
    }

    @Test
    fun `should disable enabled user`() {
        // given
        val userId = TestData.enabledUser

        // when
        val updatedUser = userService.toggleStatus(userId)

        // then
        updatedUser.apply {
            Assert.assertEquals(userId, id)
            Assert.assertFalse(enabled)
        }
    }

    private object TestData {
        val normalUser = 1L
        val adminUser = 3L

        val enabledUser = 2L
        val disabledUser = 10L
    }
}