package pl.cyganki.tournament.service

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
import pl.cyganki.tournament.EArbiterTournamentApplication
import pl.cyganki.tournament.exception.IllegalTournamentStatusException
import pl.cyganki.tournament.exception.InvalidTournamentIdException
import pl.cyganki.tournament.exception.UserIsNotAnOwnerException
import pl.cyganki.tournament.exception.WrongUserParticipateStatusException
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.tournament.testutils.MockAuthModule
import pl.cyganki.tournament.utils.SampleDataLoader
import pl.cyganki.utils.modules.AuthModuleInterface
import java.time.Duration

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

    @Autowired
    lateinit var authModuleInterface: AuthModuleInterface

    lateinit var mailService: MailService

    @Before
    fun `set up`() {
        authModuleInterface = MockAuthModule()
        mailService = Mockito.mock(MailService::class.java)
        Mockito.`when`(mailService.sendActivatedTournamentEmail(Mockito.anyString())).then { println("Mock activate mail") }
        Mockito.`when`(mailService.sendBlockedUserInTournamentEmail(Mockito.anyString(), Mockito.anyLong())).then { println("Mock removed mail") }
        Mockito.`when`(mailService.sendExtendedTournamentDeadlineEmail(Mockito.anyString())).then { println("Mock extended mail") }
        tournamentManagementService = TournamentManagementService(tournamentRepository, mailService, authModuleInterface)
        sampleDataLoader.run(null)
    }

    /**
     * blockUserInTournament
     */

    @Test
    fun `should blocked user inactive tournament in which user participates when request author is an owner`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeBlockedId = TestData.participatingActiveUserId
        val tournamentId = TestData.activeOwnerTournamentId

        val amountsOfUserBeforeBlocking = tournamentRepository.findOne(tournamentId).joinedUsersIds.size

        // when
        val tournamentAfterBlocking = tournamentManagementService.blockUserInTournament(requestAuthorId, tournamentId, userToBeBlockedId)

        // then
        tournamentAfterBlocking.apply {
            Assert.assertEquals(amountsOfUserBeforeBlocking - 1, joinedUsersIds.size)
            Assert.assertFalse(userToBeBlockedId in joinedUsersIds)
            Assert.assertTrue(userToBeBlockedId in blockedUsersIds)
        }
    }

    @Test(expected = InvalidTournamentIdException::class)
    fun `should not blocked user if tournament does not exist`() {
        // given
        val requestAuthorId = TestData.ownerUserId      // doesn't matter in this test, tournament id matters
        val userToBeBlockedId = TestData.ownerUserId    // doesn't matter in this test, tournament id matters
        val tournamentId = TestData.invalidTournamentId

        // when
        tournamentManagementService.blockUserInTournament(requestAuthorId, tournamentId, userToBeBlockedId)
    }

    @Test(expected = UserIsNotAnOwnerException::class)
    fun `should not blocked user if request author user is not an owner`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeBlockedId = TestData.participatingActiveUserId
        val tournamentId = TestData.activeNotOwnerTournamentId

        val tournamentBeforeBlocking = tournamentRepository.findOne(tournamentId)

        // when
        tournamentManagementService.blockUserInTournament(requestAuthorId, tournamentId, userToBeBlockedId)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(tournamentBeforeBlocking.joinedUsersIds.size, joinedUsersIds.size)
            Assert.assertEquals(tournamentBeforeBlocking.blockedUsersIds.size, blockedUsersIds.size)
        }
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not blocked user from finished tournament if participates`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeBlockedId = TestData.participatingFinishedUserId
        val tournamentId = TestData.finishedOwnerTournamentId

        val tournamentBeforeBlocking = tournamentRepository.findOne(tournamentId)

        // when
        tournamentManagementService.blockUserInTournament(requestAuthorId, tournamentId, userToBeBlockedId)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(tournamentBeforeBlocking.joinedUsersIds.size, joinedUsersIds.size)
            Assert.assertEquals(tournamentBeforeBlocking.blockedUsersIds.size, blockedUsersIds.size)
        }
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not blocked user from draft tournament`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeBlockedId = TestData.ownerUserId    // doesn't matter in this test, status matters
        val tournamentId = TestData.draftOwnerTournamentId

        val tournamentBeforeBlocking = tournamentRepository.findOne(tournamentId)

        // when
        tournamentManagementService.blockUserInTournament(requestAuthorId, tournamentId, userToBeBlockedId)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(tournamentBeforeBlocking.joinedUsersIds.size, joinedUsersIds.size)
            Assert.assertEquals(tournamentBeforeBlocking.blockedUsersIds.size, blockedUsersIds.size)
        }
    }

    @Test(expected = WrongUserParticipateStatusException::class)
    fun `should not blocked user if user does not participate`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeBlockedId = TestData.notParticipatingUserId
        val tournamentId = TestData.activeOwnerTournamentId

        val tournamentBeforeBlocking = tournamentRepository.findOne(tournamentId)

        // when
        tournamentManagementService.blockUserInTournament(requestAuthorId, tournamentId, userToBeBlockedId)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(tournamentBeforeBlocking.joinedUsersIds.size, joinedUsersIds.size)
            Assert.assertEquals(tournamentBeforeBlocking.blockedUsersIds.size, blockedUsersIds.size)
        }
    }

    /**
     * unblockUserInTournament
     */

    @Test
    fun `should unblocked user intournament in which user participates when request author is an owner`() {
        // given
        val requestAuthorId = TestData.ownerBlockedUserId
        val userToBeUnblockedId = TestData.blockedUserId
        val tournamentId = TestData.blockedActiveTournamentId

        val amountsOfBlockedUsersBeforeUnblocking = tournamentRepository.findOne(tournamentId).blockedUsersIds.size

        // when
        val tournamentAfterUnblocking = tournamentManagementService.unblockUserInTournament(requestAuthorId, tournamentId, userToBeUnblockedId)

        // then
        tournamentAfterUnblocking.apply {
            Assert.assertEquals(amountsOfBlockedUsersBeforeUnblocking - 1, blockedUsersIds.size)
            Assert.assertTrue(userToBeUnblockedId in joinedUsersIds)
            Assert.assertFalse(userToBeUnblockedId in blockedUsersIds)
        }
    }

    @Test(expected = InvalidTournamentIdException::class)
    fun `should not unblocked user if tournament does not exist`() {
        // given
        val requestAuthorId = TestData.ownerUserId      // doesn't matter in this test, tournament id matters
        val userToBeUnblockedId = TestData.ownerUserId    // doesn't matter in this test, tournament id matters
        val tournamentId = TestData.invalidTournamentId

        // when
        tournamentManagementService.unblockUserInTournament(requestAuthorId, tournamentId, userToBeUnblockedId)
    }

    @Test(expected = UserIsNotAnOwnerException::class)
    fun `should not unblocked user if request author user is not an owner`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeUnblockedId = TestData.participatingActiveUserId
        val tournamentId = TestData.activeNotOwnerTournamentId

        val tournamentBeforeUnblocking = tournamentRepository.findOne(tournamentId)

        // when
        tournamentManagementService.unblockUserInTournament(requestAuthorId, tournamentId, userToBeUnblockedId)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(tournamentBeforeUnblocking.joinedUsersIds.size, joinedUsersIds.size)
            Assert.assertEquals(tournamentBeforeUnblocking.blockedUsersIds.size, blockedUsersIds.size)
        }
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not unblocked user from finished tournament if participates`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeUnblockedId = TestData.participatingFinishedUserId
        val tournamentId = TestData.finishedOwnerTournamentId

        val tournamentBeforeUnblocking = tournamentRepository.findOne(tournamentId)

        // when
        tournamentManagementService.unblockUserInTournament(requestAuthorId, tournamentId, userToBeUnblockedId)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(tournamentBeforeUnblocking.joinedUsersIds.size, joinedUsersIds.size)
            Assert.assertEquals(tournamentBeforeUnblocking.blockedUsersIds.size, blockedUsersIds.size)
        }
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not unblocked user from draft tournament`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeUnblockedId = TestData.ownerUserId    // doesn't matter in this test, status matters
        val tournamentId = TestData.draftOwnerTournamentId

        val tournamentBeforeUnblocking = tournamentRepository.findOne(tournamentId)

        // when
        tournamentManagementService.unblockUserInTournament(requestAuthorId, tournamentId, userToBeUnblockedId)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(tournamentBeforeUnblocking.joinedUsersIds.size, joinedUsersIds.size)
            Assert.assertEquals(tournamentBeforeUnblocking.blockedUsersIds.size, blockedUsersIds.size)
        }
    }

    @Test(expected = WrongUserParticipateStatusException::class)
    fun `should not unblocked user if user does not participate`() {
        // given
        val requestAuthorId = TestData.ownerUserId
        val userToBeUnblockedId = TestData.notParticipatingUserId
        val tournamentId = TestData.activeOwnerTournamentId

        val tournamentBeforeUnblocking = tournamentRepository.findOne(tournamentId)

        // when
        tournamentManagementService.unblockUserInTournament(requestAuthorId, tournamentId, userToBeUnblockedId)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(tournamentBeforeUnblocking.joinedUsersIds.size, joinedUsersIds.size)
            Assert.assertEquals(tournamentBeforeUnblocking.blockedUsersIds.size, blockedUsersIds.size)
        }
    }

    /**
     * activateTournament
     */

    @Test
    fun `should activate draft tournament which user is an owner`() {
        // given
        val userId = TestData.ownerUserId
        val tournamentId = TestData.draftOwnerTournamentId

        // when
        tournamentManagementService.activateTournament(userId, tournamentId)

        // then
        Assert.assertEquals(TournamentStatus.ACTIVE, tournamentRepository.findOne(tournamentId).status)
    }

    @Test(expected = UserIsNotAnOwnerException::class)
    fun `should not activate tournament which user is not an owner`() {
        // given
        val userId = TestData.notOwnerUserId
        val tournamentId = TestData.draftOwnerTournamentId

        // when
        tournamentManagementService.activateTournament(userId, tournamentId)

        // then
        Assert.assertEquals(TournamentStatus.DRAFT, tournamentRepository.findOne(tournamentId).status)
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not activate active tournament which user is an owner`() {
        // given
        val userId = TestData.ownerUserId
        val tournamentId = TestData.activeOwnerTournamentId

        // when
        tournamentManagementService.activateTournament(userId, tournamentId)

        // then
        Assert.assertEquals(TournamentStatus.ACTIVE, tournamentRepository.findOne(tournamentId).status)
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not activate finished tournament which user is an owner`() {
        // given
        val userId = TestData.ownerUserId
        val tournamentId = TestData.finishedOwnerTournamentId

        // when
        tournamentManagementService.activateTournament(userId, tournamentId)

        // then
        Assert.assertEquals(TournamentStatus.FINISHED, tournamentRepository.findOne(tournamentId).status)
    }


    /**
     * deleteTournament
     */

    @Test
    fun `should remove draft tournament which user is an owner`() {
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

    /**
     * extendTournamentDeadline
     */

    @Test
    fun `should extend active tournament deadline which user is an owner`() {
        // given
        val userId = TestData.ownerUserId
        val tournamentId = TestData.activeOwnerTournamentId
        val tournamentsBeforeExtend = tournamentRepository.findOne(tournamentId)
        val durationToExtend = TestData.durationToExtend

        // when
        tournamentManagementService.extendTournamentDeadline(userId, tournamentId, durationToExtend)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(TournamentStatus.ACTIVE, status)
            Assert.assertEquals(tournamentsBeforeExtend.endDate.plus(durationToExtend), endDate)
        }
    }

    @Test(expected = UserIsNotAnOwnerException::class)
    fun `should not extend tournament deadline which user is not an owner`() {
        // given
        val userId = TestData.notOwnerUserId
        val tournamentId = TestData.activeOwnerTournamentId
        val tournamentsBeforeExtend = tournamentRepository.findOne(tournamentId)
        val durationToExtend = TestData.durationToExtend

        // when
        tournamentManagementService.extendTournamentDeadline(userId, tournamentId, durationToExtend)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(TournamentStatus.ACTIVE, status)
            Assert.assertEquals(tournamentsBeforeExtend.endDate, endDate)
        }
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not extend tournament deadline which user is an owner and tournament is draft`() {
        // given
        val userId = TestData.ownerUserId
        val tournamentId = TestData.draftOwnerTournamentId
        val tournamentsBeforeExtend = tournamentRepository.findOne(tournamentId)
        val durationToExtend = TestData.durationToExtend

        // when
        tournamentManagementService.extendTournamentDeadline(userId, tournamentId, durationToExtend)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(TournamentStatus.DRAFT, status)
            Assert.assertEquals(tournamentsBeforeExtend.endDate, endDate)
        }
    }

    @Test(expected = IllegalTournamentStatusException::class)
    fun `should not extend tournament deadline which user is an owner and tournament is finished`() {
        // given
        val userId = TestData.ownerUserId
        val tournamentId = TestData.finishedOwnerTournamentId
        val tournamentsBeforeExtend = tournamentRepository.findOne(tournamentId)
        val durationToExtend = TestData.durationToExtend

        // when
        tournamentManagementService.extendTournamentDeadline(userId, tournamentId, durationToExtend)

        // then
        tournamentRepository.findOne(tournamentId).apply {
            Assert.assertEquals(TournamentStatus.FINISHED, status)
            Assert.assertEquals(tournamentsBeforeExtend.endDate, endDate)
        }
    }

    private object TestData {
        val ownerUserId = 3L
        val ownerBlockedUserId = 1L
        val blockedUserId = 3L
        val notOwnerUserId = 4L
        val participatingActiveUserId = 5L
        val participatingFinishedUserId = 2L
        val notParticipatingUserId = 1L
        val draftOwnerTournamentId = "000000000000000000000002"
        val blockedActiveTournamentId = "000000000000000000000011"
        val activeOwnerTournamentId = "000000000000000000000019"
        val activeNotOwnerTournamentId = "000000000000000000000020"
        val finishedOwnerTournamentId = "000000000000000000000023"
        val invalidTournamentId = "xxx"
        val durationToExtend: Duration = Duration.ofHours(16)!!
    }
}

