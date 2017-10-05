package pl.cyganki.tournament.service

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import pl.cyganki.tournament.EArbiterTournamentApplication
import pl.cyganki.tournament.exception.IllegalTournamentStatusException
import pl.cyganki.tournament.exception.InvalidTournamentIdException
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.model.dto.TaskPreview
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.tournament.testutils.MockAuthModule
import pl.cyganki.tournament.testutils.MockTourResModule
import pl.cyganki.utils.modules.AuthModuleInterface
import pl.cyganki.utils.modules.TournamentResultsModuleInterface

/**
 * Tournaments to tests are defined in proper JSONs
 * @see /resources/db/changelog/test-data/'*'
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(EArbiterTournamentApplication::class), loader = SpringBootContextLoader::class)
@ActiveProfiles("test")
class TournamentDetailsServiceTest {

    lateinit var tournamentDetailsService: TournamentDetailsService

    lateinit var tourResModule: TournamentResultsModuleInterface

    lateinit var authModule: AuthModuleInterface

    @Autowired
    lateinit var tournamentRepository: TournamentRepository

    @Before
    fun `set up`() {
        authModule = MockAuthModule()
        tourResModule = MockTourResModule()
        tournamentDetailsService = TournamentDetailsService(tournamentRepository, authModule, tourResModule)
    }

    @Test(expected = InvalidTournamentIdException::class)

    fun `should throw invalidTournamentIdException for id for non-existing tournament`() {
        // given
        val tournamentId = TestData.nonExistingTournamentId
        val userId = TestData.userId

        // when
        tournamentDetailsService.getTournamentDetailsForUser(userId, tournamentId)

    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should throw IllegalTournamentStatusException for existing draft tournament where user is not an owner`() {
        // given
        val tournamentId = TestData.notUserDraftTournamentId
        val userId = TestData.userId

        // when
        tournamentDetailsService.getTournamentDetailsForUser(userId, tournamentId)
    }

    @Test
    fun `should return only basic details for private tournament in which user does not participate`() {
        // given
        val tournamentId = TestData.privateForbiddenTournamentId
        val userId = TestData.userId

        // when
        val foundDetails = tournamentDetailsService.getTournamentDetailsForUser(userId, tournamentId)

        // then - check active-tournament-4.json
        foundDetails.apply {
            Assert.assertEquals(tournamentId, id)
            Assert.assertEquals("laborum tempor culpa quis sit non", name)
            Assert.assertEquals(TournamentStatus.ACTIVE, status)
            Assert.assertFalse(accessDetails.publicFlag)
            Assert.assertFalse(accessDetails.owner)
            Assert.assertFalse(accessDetails.participateInTournament)
            Assert.assertNull(description)
            Assert.assertNull(users)
            Assert.assertNull(startDate)
            Assert.assertNull(endDate)
            Assert.assertEquals(emptyList<TaskPreview>(),taskPreviews)
            Assert.assertNull(maxPoints)
            Assert.assertNull(earnedPoints)
        }
    }

    @Test
    fun `should return basic details with basic tasks details for public tournament in which user does not participate`() {
        // given
        val tournamentId = TestData.publicNotParticipateTournamentId
        val userId = TestData.userId

        // when
        val foundDetails = tournamentDetailsService.getTournamentDetailsForUser(userId, tournamentId)

        // then - check active-tournament-3.json
        foundDetails.apply {
            Assert.assertEquals(tournamentId, id)
            Assert.assertEquals("voluptate tempor non ea ullamco ut", name)
            Assert.assertEquals(TournamentStatus.ACTIVE, status)
            Assert.assertTrue(accessDetails.publicFlag)
            Assert.assertFalse(accessDetails.owner)
            Assert.assertFalse(accessDetails.participateInTournament)
            Assert.assertNotNull(description)
            Assert.assertNotNull(users)
            Assert.assertNotNull(startDate)
            Assert.assertNotNull(endDate)
            Assert.assertNotNull(taskPreviews)
            taskPreviews.forEach { Assert.assertNull(it.taskUserDetails) }
            Assert.assertNotNull(maxPoints)
            Assert.assertNull(earnedPoints)
        }
    }

    @Test
    fun `should return basic details with basic tasks details for public tournament where user is an owner`() {
        // given
        val tournamentId = TestData.publicOwnerTournamentId
        val userId = TestData.userId

        // when
        val foundDetails = tournamentDetailsService.getTournamentDetailsForUser(userId, tournamentId)

        // then - check active-tournament-9.json
        foundDetails.apply {
            Assert.assertEquals(tournamentId, id)
            Assert.assertEquals("tempor deserunt ex non quis elit", name)
            Assert.assertEquals(TournamentStatus.ACTIVE, status)
            Assert.assertTrue(accessDetails.publicFlag)
            Assert.assertTrue(accessDetails.owner)
            Assert.assertFalse(accessDetails.participateInTournament)
            Assert.assertNotNull(description)
            Assert.assertNotNull(users)
            Assert.assertNotNull(startDate)
            Assert.assertNotNull(endDate)
            Assert.assertNotNull(taskPreviews)
            taskPreviews.forEach { Assert.assertNull(it.taskUserDetails) }
            Assert.assertNotNull(maxPoints)
            Assert.assertNull(earnedPoints)
        }
    }

    @Test
    fun `should return all details for private active tournament in which user participates`() {
        // given
        val tournamentId = TestData.privateParticipateTournamentId
        val userId = TestData.userId

        // when
        val foundDetails = tournamentDetailsService.getTournamentDetailsForUser(userId, tournamentId)

        // then - check active-tournament-1.json
        foundDetails.apply {
            Assert.assertEquals(tournamentId, id)
            Assert.assertEquals("aliqua tempor excepteur ea consectetur ad", name)
            Assert.assertEquals(TournamentStatus.ACTIVE, status)
            Assert.assertFalse(accessDetails.publicFlag)
            Assert.assertFalse(accessDetails.owner)
            Assert.assertTrue(accessDetails.participateInTournament)
            Assert.assertNotNull(description)
            Assert.assertNotNull(users)
            Assert.assertNotNull(startDate)
            Assert.assertNotNull(endDate)
            Assert.assertNotNull(taskPreviews)
            taskPreviews.forEach { Assert.assertNotNull(it.taskUserDetails) }
            Assert.assertNotNull(maxPoints)
            Assert.assertNotNull(earnedPoints)
        }
    }

    private object TestData {
        val nonExistingTournamentId = "xxx"
        val notUserDraftTournamentId = "000000000000000000000003"
        val publicNotParticipateTournamentId = "000000000000000000000013"
        val privateForbiddenTournamentId = "000000000000000000000014"
        val privateParticipateTournamentId = "000000000000000000000011"
        val publicOwnerTournamentId = "000000000000000000000019"
        val userId = 3L
    }
}