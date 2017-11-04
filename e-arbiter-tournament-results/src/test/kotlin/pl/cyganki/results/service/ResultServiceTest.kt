package pl.cyganki.results.service

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional
import pl.cyganki.results.EArbiterTournamentResultsApplication
import pl.cyganki.results.repository.ResultRepository
import pl.cyganki.utils.model.tournamentresults.UsersTasksList
import pl.cyganki.utils.modules.AuthModuleInterface

//getTournamentResults
/**
 * Tournaments to tests are defined in proper JSONs
 * @see /resources/db/changelog/test-data/'*'
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(EArbiterTournamentResultsApplication::class), loader = SpringBootContextLoader::class)
@ActiveProfiles("test")
@Transactional
class ResultServiceTest {

    lateinit var resultService: ResultService

    @Autowired
    lateinit var userTaskDetailsService: UserTaskDetailsService

    @Autowired
    lateinit var resultRepository: ResultRepository

    lateinit var authModuleInterface: AuthModuleInterface


    @Before
    fun `set up`() {
        authModuleInterface = Mockito.mock(AuthModuleInterface::class.java)
        resultService = ResultService(userTaskDetailsService, resultRepository, authModuleInterface)
    }

    @Test
    fun `should return valid tournament results for closed tournament with two users with the same amount of points`() {
        // given
        val usersAndTasks = UsersTasksList(
                listOf(19, 10, 15, 7),
                (184..190).map { "000000000000000000000$it" }
        )
        val tournamentId = "000000000000000000000022"

        Mockito.`when`(authModuleInterface.getUserNamesByIds(usersAndTasks.users.toTypedArray())).thenReturn(
                mapOf(
                        7L to "7",
                        10L to "10",
                        15L to "15",
                        19L to "19"
                )
        )

        // when
        val results = resultService.getTournamentResults(tournamentId, usersAndTasks)

        // then
        results.apply {
            Assert.assertEquals(4, size)
            for (i in 0..2) {
                for (j in i + 1..3) {
                    Assert.assertTrue(results[i].position <= results[j].position)
                    Assert.assertTrue(results[i].summaryPoints >= results[j].summaryPoints)
                }
            }

            results.forEach { Assert.assertEquals(usersAndTasks.tasks.size, it.taskResults.size) }
        }
    }
}