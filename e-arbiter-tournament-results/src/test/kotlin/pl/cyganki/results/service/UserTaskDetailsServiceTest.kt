package pl.cyganki.results.service

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import pl.cyganki.results.EArbiterTournamentResultsApplication

/**
 * Used data to tests are defined in proper liquibase XML
 * @see /resources/db/changelog/test-data/db.changelog-test-data.xml
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(EArbiterTournamentResultsApplication::class), loader = SpringBootContextLoader::class)
@ActiveProfiles("test")
class UserTaskDetailsServiceTest {

    @Autowired
    private lateinit var userTaskDetailsService: UserTaskDetailsService

    /**
     * getTaskUserDetails
     */

    @Test
    fun `should return details for task with one result from tournament in which user participates`() {
        // given
        val tournamentId = TestData.validTournamentId
        val oneResultTaskId = TestData.validTaskIdWithOneResult
        val userId = TestData.userId

        // when
        val foundDetails = userTaskDetailsService.getTaskUserDetails(tournamentId, oneResultTaskId, userId)

        // then
        foundDetails.apply {
            Assert.assertEquals(1, earnedPoints)
            Assert.assertEquals(oneResultTaskId, taskId)
            Assert.assertEquals(1, userAttempts)
        }
    }

    @Test
    fun `should return details for task with more results from tournament in which user participates`() {
        // given
        val tournamentId = TestData.validTournamentId
        val fourResultsTaskId = TestData.validTaskIdWithFourResult
        val userId = TestData.userId

        // when
        val foundDetails = userTaskDetailsService.getTaskUserDetails(tournamentId, fourResultsTaskId, userId)

        // then
        foundDetails.apply {
            Assert.assertEquals(2, earnedPoints)
            Assert.assertEquals(fourResultsTaskId, taskId)
            Assert.assertEquals(4, userAttempts)
        }
    }

    @Test
    fun `should return empty details for task from tournament in which user does not participate`() {
        // given
        val invalidTournamentId = TestData.invalidTournamentId
        val invalidTaskId = TestData.invalidTaskId
        val userId = TestData.userId

        // when
        val foundDetails = userTaskDetailsService.getTaskUserDetails(invalidTournamentId, invalidTaskId, userId)

        // then
        foundDetails.apply {
            Assert.assertEquals(0, earnedPoints)
            Assert.assertEquals(invalidTaskId, taskId)
            Assert.assertEquals(0, userAttempts)
        }
    }

    /**
     * getAllTasksUserDetailsInTournament
     */

    @Test
    fun `should return whole map with user details for tournament in which user participates`() {
        // given
        val tournamentId = TestData.validTournamentId
        val tasksIds = listOf(TestData.validTaskIdWithFourResult, TestData.validTaskIdWithOneResult)
        val userId = TestData.userId

        // when
        val foundDetailsMap = userTaskDetailsService.getAllTasksUserDetailsInTournament(tournamentId, tasksIds, userId)

        // then
        foundDetailsMap.apply {
            Assert.assertEquals(2, size)
            Assert.assertNotNull(get(TestData.validTaskIdWithFourResult))

            get(TestData.validTaskIdWithFourResult)!!.apply {
                Assert.assertEquals(2, earnedPoints)
                Assert.assertEquals(TestData.validTaskIdWithFourResult, taskId)
                Assert.assertEquals(4, userAttempts)
            }

            Assert.assertNotNull(get(TestData.validTaskIdWithOneResult))

            get(TestData.validTaskIdWithOneResult)!!.apply {
                Assert.assertEquals(1, earnedPoints)
                Assert.assertEquals(TestData.validTaskIdWithOneResult, taskId)
                Assert.assertEquals(1, userAttempts)
            }
        }
    }

    @Test
    fun `should return empty map when task ids are not provided`() {
        // given
        val tournamentId = TestData.validTournamentId
        val tasksIds = listOf<String>()
        val userId = TestData.userId

        // when
        val foundDetailsMap = userTaskDetailsService.getAllTasksUserDetailsInTournament(tournamentId, tasksIds, userId)

        // then
        foundDetailsMap.apply {
            Assert.assertEquals(0, size)
        }
    }

    @Test
    fun `should return map with empty details when user does not participate`() {
        // given
        val tournamentId = TestData.invalidTournamentId
        val tasksIds = listOf(TestData.validTaskIdWithFourResult, TestData.validTaskIdWithOneResult)
        val userId = TestData.userId

        // when
        val foundDetailsMap = userTaskDetailsService.getAllTasksUserDetailsInTournament(tournamentId, tasksIds, userId)

        // then
        foundDetailsMap.apply {
            Assert.assertEquals(2, size)
            get(TestData.validTaskIdWithOneResult)!!.apply {
                Assert.assertEquals(0, earnedPoints)
                Assert.assertEquals(TestData.validTaskIdWithOneResult, taskId)
                Assert.assertEquals(0, userAttempts)
            }

            get(TestData.validTaskIdWithFourResult)!!.apply {
                Assert.assertEquals(0, earnedPoints)
                Assert.assertEquals(TestData.validTaskIdWithFourResult, taskId)
                Assert.assertEquals(0, userAttempts)
            }
        }
    }

    private object TestData {
        val userId = 3L

        val validTournamentId = "000000000000000000000011"
        val invalidTournamentId = "000000000000000000000002"    // some draft

        val validTaskIdWithOneResult = "000000000000000000000095"
        val validTaskIdWithFourResult = "000000000000000000000097"
        val invalidTaskId = "000000000000000000000006"
    }
}