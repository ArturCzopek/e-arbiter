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
import pl.cyganki.tournament.exception.UserIsNotAnOwnerException
import pl.cyganki.tournament.exception.WrongUserParticipateStatusException
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.tournament.utils.SampleDataLoader

/**
 * Tournaments to tests are defined in proper JSONs
 * @see /resources/db/changelog/test-data/'*'
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(EArbiterTournamentApplication::class), loader = SpringBootContextLoader::class)
@ActiveProfiles("test")
class TournamentManagementServiceTest {

    lateinit var tournamentManagementService: TournamentManagementService

    @Autowired
    lateinit var tournamentRepository: TournamentRepository

    @Autowired
    lateinit var sampleDataLoader: SampleDataLoader

    @Before
    fun `set up`() {
        tournamentManagementService = TournamentManagementService(tournamentRepository)
        sampleDataLoader.run(null)
    }

    /**
     * removeUserFromTournament
     */

    @Test
    fun `should removed user from active tournament in which user participates when request author is an owner`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeRemovedId = TestData.participatingActiveUserId
        val tournamentId = TestData.activeOwnerTournamentId

        val amountsOfUserBeforeRemoving = tournamentRepository.findOne(tournamentId).joinedUsersIds.size

        // when
        val tournamentAfterRemoving = tournamentManagementService.removeUserFromTournament(requestAuthorId, userToBeRemovedId, tournamentId)

        // then
        tournamentAfterRemoving.apply {
            Assert.assertEquals(amountsOfUserBeforeRemoving - 1, joinedUsersIds.size)
            Assert.assertFalse(joinedUsersIds.contains(userToBeRemovedId))
        }
    }

    @Test(expected = InvalidTournamentIdException::class)
    fun `should not removed user if tournament does not exist`() {
        // given
        val requestAuthorId = TestData.ownerUserId      // doesn't matter in this test, tournament id matters
        val userToBeRemovedId = TestData.ownerUserId    // doesn't matter in this test, tournament id matters
        val tournamentId = TestData.invalidTournamentId

        // when
        tournamentManagementService.removeUserFromTournament(requestAuthorId, userToBeRemovedId, tournamentId)
    }

    @Test(expected = UserIsNotAnOwnerException::class)
    fun `should not removed user if request author user is not an owner`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeRemovedId = TestData.participatingActiveUserId
        val tournamentId = TestData.activeNotOwnerTournamentId

        val amountsOfUserBeforeRemoving = tournamentRepository.findOne(tournamentId).joinedUsersIds.size

        // when
        tournamentManagementService.removeUserFromTournament(requestAuthorId, userToBeRemovedId, tournamentId)

        // then
        val amountsOfUserAfterRemoving = tournamentRepository.findOne(tournamentId).joinedUsersIds.size
        Assert.assertEquals(amountsOfUserBeforeRemoving, amountsOfUserAfterRemoving)
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not removed user from finished tournament if participates`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeRemovedId = TestData.participatingFinishedUserId
        val tournamentId = TestData.finishedOwnerTournamentId

        val amountsOfUserBeforeRemoving = tournamentRepository.findOne(tournamentId).joinedUsersIds.size

        // when
        tournamentManagementService.removeUserFromTournament(requestAuthorId, userToBeRemovedId, tournamentId)

        // then
        val amountsOfUserAfterRemoving = tournamentRepository.findOne(tournamentId).joinedUsersIds.size
        Assert.assertEquals(amountsOfUserBeforeRemoving, amountsOfUserAfterRemoving)
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not removed user from draft tournament`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeRemovedId = TestData.ownerUserId    // doesn't matter in this test, status matters
        val tournamentId = TestData.draftOwnerTournamentId

        val amountsOfUserBeforeRemoving = tournamentRepository.findOne(tournamentId).joinedUsersIds.size

        // when
        tournamentManagementService.removeUserFromTournament(requestAuthorId, userToBeRemovedId, tournamentId)

        // then
        val amountsOfUserAfterRemoving = tournamentRepository.findOne(tournamentId).joinedUsersIds.size
        Assert.assertEquals(amountsOfUserBeforeRemoving, amountsOfUserAfterRemoving)
    }

    @Test(expected = WrongUserParticipateStatusException::class)
    fun `should not removed user if user does not participate`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeRemovedId = TestData.notParticipatingUserId
        val tournamentId = TestData.activeOwnerTournamentId

        val amountsOfUserBeforeRemoving = tournamentRepository.findOne(tournamentId).joinedUsersIds.size

        // when
        tournamentManagementService.removeUserFromTournament(requestAuthorId, userToBeRemovedId, tournamentId)

        // then
        val amountsOfUserAfterRemoving = tournamentRepository.findOne(tournamentId).joinedUsersIds.size
        Assert.assertEquals(amountsOfUserBeforeRemoving, amountsOfUserAfterRemoving)
    }

    /**
     * activateTournament
     */

    // TODO: Maciek - missing tests


    /**
     * deleteTournament
     */

    @Test
    fun `should remove tournament which user is an owner`() {
        // given
        val userId = TestData.ownerUserId
        val tournamentId = TestData.draftOwnerTournamentId
        val tournamentsBeforeDelete = tournamentRepository.count()

        // when
        tournamentManagementService.deleteTournament(userId, tournamentId)

        // then
        tournamentRepository.apply {
            Assert.assertNull(findOne(tournamentId))
            Assert.assertEquals(tournamentsBeforeDelete - 1, count())
        }
    }

    @Test(expected = UserIsNotAnOwnerException::class)
    fun `should not remove tournament which user is not an owner`() {
        // given
        val userId = TestData.notOwnerUserId
        val tournamentId = TestData.draftOwnerTournamentId
        val tournamentsBeforeDelete = tournamentRepository.count()

        // when
        tournamentManagementService.deleteTournament(userId, tournamentId)

        // then
        tournamentRepository.apply {
            Assert.assertNull(findOne(tournamentId))
            Assert.assertEquals(tournamentsBeforeDelete, count())
        }
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not remove tournament which user is an owner and tournament is active`() {
        // given
        val userId = TestData.ownerUserId
        val tournamentId = TestData.activeOwnerTournamentId
        val tournamentsBeforeDelete = tournamentRepository.count()

        // when
        tournamentManagementService.deleteTournament(userId, tournamentId)

        // then
        tournamentRepository.apply {
            Assert.assertNull(findOne(tournamentId))
            Assert.assertEquals(tournamentsBeforeDelete, count())
        }
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not remove tournament which user is an owner and tournament is finished`() {
        // given
        val userId = TestData.ownerUserId
        val tournamentId = TestData.finishedOwnerTournamentId
        val tournamentsBeforeDelete = tournamentRepository.count()

        // when
        tournamentManagementService.deleteTournament(userId, tournamentId)

        // then
        tournamentRepository.apply {
            Assert.assertNull(findOne(tournamentId))
            Assert.assertEquals(tournamentsBeforeDelete, count())
        }
    }

    private object TestData {
        val ownerUserId = 3L
        val notOwnerUserId = 4L
        val participatingActiveUserId = 5L
        val participatingFinishedUserId = 2L
        val notParticipatingUserId = 1L
        val draftOwnerTournamentId = "000000000000000000000002"
        val activeOwnerTournamentId = "000000000000000000000019"
        val activeNotOwnerTournamentId = "000000000000000000000020"
        val finishedOwnerTournamentId = "000000000000000000000023"
        val invalidTournamentId = "xxx"
    }
}

