package pl.cyganki.tournament.service

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import pl.cyganki.tournament.EArbiterTournamentApplication
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.utils.modules.AuthModuleInterface
import pl.cyganki.utils.security.dto.User

/**
 * Tournaments to tests are defined in proper JSONs
 * @see /resources/db/changelog/test-data/'*'
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(EArbiterTournamentApplication::class), loader = SpringBootContextLoader::class)
@ActiveProfiles("test")
class TournamentPreviewsFetcherTest {

    lateinit var tournamentPreviewsFetcher: TournamentPreviewsFetcher

    lateinit var authModule: AuthModuleInterface

    @Autowired
    lateinit var tournamentRepository: TournamentRepository

    val userId = 3L

    @Before
    fun `set up`() {
        authModule = MockAuthModule()
        tournamentPreviewsFetcher = TournamentPreviewsFetcher(tournamentRepository, authModule)
    }

    @Test
    fun `should return all active user's tournaments in which user participates in one page`() {
        // given
        val pageRequest = PageRequest(0, 10)

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getActiveTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(2, foundTournamentsPage.totalElements)
        Assert.assertEquals(2, foundTournamentsPage.numberOfElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertEquals(TournamentStatus.ACTIVE, foundTournamentsPage.content[0].status)
        Assert.assertEquals(TournamentStatus.ACTIVE, foundTournamentsPage.content[1].status)
    }

    @Test
    fun `should return one page active user's tournament in which user participates and information about two available pages`() {
        // given
        val pageRequest = PageRequest(0, 1)

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getActiveTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(2, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.numberOfElements)
        Assert.assertEquals(2, foundTournamentsPage.totalPages)
        Assert.assertFalse(foundTournamentsPage.isLast)
        Assert.assertEquals(TournamentStatus.ACTIVE, foundTournamentsPage.content[0].status)
    }

    @Test
    fun `should return all active user's tournaments in which user participates sorted descending by name`() {
        // given
        val pageRequest = PageRequest(0, 10, Sort(Sort.Direction.DESC, "name"))

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getActiveTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(2, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertEquals(TournamentStatus.ACTIVE, foundTournamentsPage.content[0].status)
        Assert.assertEquals(TournamentStatus.ACTIVE, foundTournamentsPage.content[1].status)
        Assert.assertTrue(foundTournamentsPage.content[0].name.compareTo(foundTournamentsPage.content[1].name) > 0)
    }

    @Test
    fun `should return all active user's tournaments in which user participates in two pages sorted ascending by name`() {
        // given
        val pageRequest = PageRequest(0, 10, Sort("name"))

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getActiveTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(2, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertEquals(TournamentStatus.ACTIVE, foundTournamentsPage.content[0].status)
        Assert.assertEquals(TournamentStatus.ACTIVE, foundTournamentsPage.content[1].status)
        Assert.assertTrue(foundTournamentsPage.content[0].name.compareTo(foundTournamentsPage.content[1].name) < 0)
    }

    @Test
    fun `should return one active user's tournaments in which user participates by query`() {
        // given
        val pageRequest = PageRequest(0, 10)
        val query = "Private!"  // in this case, it should be found in name

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getActiveTournamentsInWhichUserParticipates(userId, pageRequest, query)

        // then
        Assert.assertEquals(1, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertEquals(TournamentStatus.ACTIVE, foundTournamentsPage.content[0].status)
        Assert.assertEquals("Active Tournament - private!", foundTournamentsPage.content[0].name)
    }

    @Test
    fun `should return all finished user's tournaments in which user participates in one page`() {
        // given
        val pageRequest = PageRequest(0, 10)

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getFinishedTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(2, foundTournamentsPage.totalElements)
        Assert.assertEquals(2, foundTournamentsPage.numberOfElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertEquals(TournamentStatus.FINISHED, foundTournamentsPage.content[0].status)
        Assert.assertEquals(TournamentStatus.FINISHED, foundTournamentsPage.content[1].status)
    }

    @Test
    fun `should return one page finished user's tournament in which user participates and information about two available pages`() {
        // given
        val pageRequest = PageRequest(1, 1)

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getFinishedTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(2, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.numberOfElements)
        Assert.assertEquals(2, foundTournamentsPage.totalPages)
        Assert.assertFalse(foundTournamentsPage.isFirst)
        Assert.assertEquals(TournamentStatus.FINISHED, foundTournamentsPage.content[0].status)
    }

    @Test
    fun `should return all finished user's tournaments in which user participates sorted descending by name`() {
        // given
        val pageRequest = PageRequest(0, 10, Sort(Sort.Direction.DESC, "name"))

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getFinishedTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(2, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertEquals(TournamentStatus.FINISHED, foundTournamentsPage.content[0].status)
        Assert.assertEquals(TournamentStatus.FINISHED, foundTournamentsPage.content[1].status)
        Assert.assertTrue(foundTournamentsPage.content[0].name.compareTo(foundTournamentsPage.content[1].name) > 0)
    }

    @Test
    fun `should return all finished user's tournaments in which user participates in two pages sorted ascending by name`() {
        // given
        val pageRequest = PageRequest(0, 10, Sort("name"))

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getFinishedTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(2, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertEquals(TournamentStatus.FINISHED, foundTournamentsPage.content[0].status)
        Assert.assertEquals(TournamentStatus.FINISHED, foundTournamentsPage.content[1].status)
        Assert.assertTrue(foundTournamentsPage.content[0].name.compareTo(foundTournamentsPage.content[1].name) < 0)
    }

    @Test
    fun `should return one finished user's tournaments in which user participates by query`() {
        // given
        val pageRequest = PageRequest(0, 10)
        val query = "haha"  // in this case, it should be found in description

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getFinishedTournamentsInWhichUserParticipates(userId, pageRequest, query)

        // then
        Assert.assertEquals(1, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertEquals(TournamentStatus.FINISHED, foundTournamentsPage.content[0].status)
        Assert.assertEquals("Finished Tournament - private!", foundTournamentsPage.content[0].name)
        Assert.assertEquals("UHAHAHAHHAHA", foundTournamentsPage.content[0].description)
    }

    inner class MockAuthModule : AuthModuleInterface {
        override fun getToken() = throw UnsupportedOperationException("This mock shouldn't call this method")

        override fun getUser(token: String) = throw UnsupportedOperationException("This mock shouldn't call this method")

        override fun getUserFromRequest(user: User) = throw UnsupportedOperationException("This mock shouldn't call this method")

        override fun getUserNameById(id: Long) = "Test Owner"
    }
}