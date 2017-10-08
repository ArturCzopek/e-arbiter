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
import pl.cyganki.tournament.exception.*
import pl.cyganki.tournament.model.dto.TournamentUserActionRequest
import pl.cyganki.tournament.model.dto.TournamentUserActionType
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
class TournamentUserActionServiceTest {

    lateinit var tournamentUserActionService: TournamentUserActionService

    @Autowired
    lateinit var tournamentRepository: TournamentRepository

    @Autowired
    lateinit var hashingService: HashingService

    @Autowired
    lateinit var sampleDataLoader: SampleDataLoader

    @Before
    fun `set up`() {
        tournamentUserActionService = TournamentUserActionService(tournamentRepository, hashingService)
        sampleDataLoader.run(null)
    }

    /**
     * joinToTournament
     */

    @Test
    fun `should join user to public active tournament`() {
        // given
        val tournamentId = TestData.activePublicTournamentIdInWhichUserDoesNotParticipate
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.JOIN, tournamentId)

        // when
        val hasJoined = tournamentUserActionService.joinToTournament(userId, joinRequest)
        val tournamentAfterJoin = tournamentRepository.findOne(tournamentId)

        // then
        Assert.assertTrue(hasJoined)
        Assert.assertTrue(tournamentAfterJoin.joinedUsersIds.contains(userId))
    }

    @Test
    fun `should join user to private active tournament`() {
        // given
        val tournamentId = TestData.activePrivateTournamentIdInWhichUserDoesNotParticipate
        val password = TestData.activePrivateTournamentPasswordInWhichUserDoesNotParticipate
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.JOIN, tournamentId, password)

        // when
        val hasJoined = tournamentUserActionService.joinToTournament(userId, joinRequest)
        val tournamentAfterJoin = tournamentRepository.findOne(tournamentId)

        // then
        Assert.assertTrue(hasJoined)
        Assert.assertTrue(tournamentAfterJoin.joinedUsersIds.contains(userId))
    }

    @Test(expected = InvalidTournamentIdException::class)
    fun `should throw exception for wrong tournament id to join`() {
        // given
        val tournamentId = TestData.wrongTournamentId
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.JOIN, tournamentId)

        // when
        tournamentUserActionService.joinToTournament(userId, joinRequest)
    }

    @Test(expected = IllegalTournamentUserActionTypeException::class)
    fun `should throw exception for wrong request type (not joining)`() {
        // given
        val tournamentId = TestData.activePublicTournamentIdInWhichUserDoesNotParticipate
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.LEAVE, tournamentId)

        // when
        tournamentUserActionService.joinToTournament(userId, joinRequest)
    }

    @Test(expected = UserIsAnOwnerException::class)
    fun `should throw exception for joining to owned tournament`() {
        // given
        val tournamentId = TestData.activePublicTournamentIdWhereUserIsAnOwner
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.JOIN, tournamentId)

        // when
        tournamentUserActionService.joinToTournament(userId, joinRequest)
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should throw exception for joining to non-active tournament`() {
        // given
        val tournamentId = TestData.finishedPublicTournamentIdInWhichUserDoesNotParticipate
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.JOIN, tournamentId)

        // when
        tournamentUserActionService.joinToTournament(userId, joinRequest)
    }

    @Test(expected = WrongUserParticipateStatusException::class)
    fun `should throw exception for joining to tournament in which user already participates`() {
        // given
        val tournamentId = TestData.activePublicTournamentIdInWhichUserParticipates
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.JOIN, tournamentId)

        // when
        tournamentUserActionService.joinToTournament(userId, joinRequest)
    }

    @Test(expected = IncorrectPasswordException::class)
    fun `should throw exception for joining to private tournament with wrong password`() {
        // given
        val tournamentId = TestData.activePrivateTournamentIdInWhichUserDoesNotParticipate
        val userId = TestData.userId
        val password = TestData.activePrivateTournamentInvalidPasswordInWhichUserDoesNotParticipate
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.JOIN, tournamentId, password)

        // when
        tournamentUserActionService.joinToTournament(userId, joinRequest)
    }


    /**
     * leaveTournament
     */

    @Test
    fun `should leave user public active tournament`() {
        // given
        val tournamentId = TestData.activePublicTournamentIdInWhichUserParticipates
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.LEAVE, tournamentId)

        // when
        val hasLeft = tournamentUserActionService.leaveTournament(userId, joinRequest)
        val tournamentAfterJoin = tournamentRepository.findOne(tournamentId)

        // then
        Assert.assertTrue(hasLeft)
        Assert.assertFalse(tournamentAfterJoin.joinedUsersIds.contains(userId))
    }

    @Test(expected = InvalidTournamentIdException::class)
    fun `should throw exception for wrong tournament id to leave`() {
        // given
        val tournamentId = TestData.wrongTournamentId
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.LEAVE, tournamentId)

        // when
        tournamentUserActionService.leaveTournament(userId, joinRequest)
    }

    @Test(expected = IllegalTournamentUserActionTypeException::class)
    fun `should throw exception for wrong request type (not leaving)`() {
        // given
        val tournamentId = TestData.activePublicTournamentIdInWhichUserParticipates
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.JOIN, tournamentId)

        // when
        tournamentUserActionService.leaveTournament(userId, joinRequest)
    }

    @Test(expected = UserIsAnOwnerException::class)
    fun `should throw exception for leaving owned tournament`() {
        // given
        val tournamentId = TestData.activePublicTournamentIdWhereUserIsAnOwner
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.LEAVE, tournamentId)

        // when
        tournamentUserActionService.leaveTournament(userId, joinRequest)
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should throw exception for leaving non-active tournament`() {
        // given
        val tournamentId = TestData.finishedPublicTournamentIdInWhichUserParticipates
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.LEAVE, tournamentId)

        // when
        tournamentUserActionService.leaveTournament(userId, joinRequest)
    }

    @Test(expected = WrongUserParticipateStatusException::class)
    fun `should throw exception for leaving tournament in which user already does not participate`() {
        // given
        val tournamentId = TestData.activePrivateTournamentIdInWhichUserDoesNotParticipate
        val userId = TestData.userId
        val joinRequest = TournamentUserActionRequest(TournamentUserActionType.LEAVE, tournamentId)

        // when
        tournamentUserActionService.leaveTournament(userId, joinRequest)
    }

    private object TestData {
        val wrongTournamentId = "xxx"
        val activePublicTournamentIdInWhichUserDoesNotParticipate = "000000000000000000000012"
        val activePublicTournamentIdInWhichUserParticipates = "000000000000000000000011"
        val activePublicTournamentIdWhereUserIsAnOwner = "000000000000000000000017"
        val activePrivateTournamentIdInWhichUserDoesNotParticipate = "000000000000000000000014"
        val finishedPublicTournamentIdInWhichUserDoesNotParticipate = "000000000000000000000021"
        val finishedPublicTournamentIdInWhichUserParticipates = "000000000000000000000024"
        val activePrivateTournamentPasswordInWhichUserDoesNotParticipate = "password118591"
        val activePrivateTournamentInvalidPasswordInWhichUserDoesNotParticipate = "xoxoxox"
        val userId = 3L
    }
}