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

    private val userId = 3L

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
        Assert.assertEquals(6, foundTournamentsPage.totalElements)
        Assert.assertEquals(6, foundTournamentsPage.numberOfElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        foundTournamentsPage.content
                .forEach { Assert.assertEquals(TournamentStatus.ACTIVE, it.status) }
    }

    @Test
    fun `should return one page active user's tournament in which user participates and information about two available pages`() {
        // given
        val pageRequest = PageRequest(0, 4)

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getActiveTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(6, foundTournamentsPage.totalElements)
        Assert.assertEquals(4, foundTournamentsPage.numberOfElements)
        Assert.assertEquals(2, foundTournamentsPage.totalPages)
        Assert.assertFalse(foundTournamentsPage.isLast)
        foundTournamentsPage.content
                .forEach { Assert.assertEquals(TournamentStatus.ACTIVE, it.status) }
    }

    @Test
    fun `should return all active user's tournaments in which user participates sorted descending by name`() {
        // given
        val pageRequest = PageRequest(0, 10, Sort(Sort.Direction.DESC, "name"))

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getActiveTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(6, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertTrue(foundTournamentsPage.content[0].name > foundTournamentsPage.content[1].name)
        Assert.assertTrue(foundTournamentsPage.content[1].name > foundTournamentsPage.content[2].name)
        Assert.assertTrue(foundTournamentsPage.content[0].name > foundTournamentsPage.content[3].name)
        Assert.assertTrue(foundTournamentsPage.content[3].name > foundTournamentsPage.content[5].name)

        foundTournamentsPage.content
                .forEach { Assert.assertEquals(TournamentStatus.ACTIVE, it.status) }
    }

    @Test
    fun `should return all active user's tournaments in which user participates in two pages sorted ascending by name`() {
        // given
        val pageRequest = PageRequest(0, 10, Sort("name"))

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getActiveTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(6, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertTrue(foundTournamentsPage.content[0].name < foundTournamentsPage.content[1].name)
        Assert.assertTrue(foundTournamentsPage.content[1].name < foundTournamentsPage.content[2].name)
        Assert.assertTrue(foundTournamentsPage.content[0].name < foundTournamentsPage.content[3].name)
        Assert.assertTrue(foundTournamentsPage.content[3].name < foundTournamentsPage.content[5].name)

        foundTournamentsPage.content
                .forEach { Assert.assertEquals(TournamentStatus.ACTIVE, it.status) }
    }

    @Test
    fun `should return one active user's tournaments in which user participates by query`() {
        // given
        val pageRequest = PageRequest(0, 10)
        val tournamentToFindName = "in est culpa tempor non consequat"
        val query = tournamentToFindName.substring(1, 11)

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getActiveTournamentsInWhichUserParticipates(userId, pageRequest, query)

        // then
        Assert.assertEquals(1, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertEquals(TournamentStatus.ACTIVE, foundTournamentsPage.content[0].status)
        Assert.assertEquals(tournamentToFindName, foundTournamentsPage.content[0].name)
    }

    @Test
    fun `should return all finished user's tournaments in which user participates in one page`() {
        // given
        val pageRequest = PageRequest(0, 10)

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getFinishedTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(5, foundTournamentsPage.totalElements)
        Assert.assertEquals(5, foundTournamentsPage.numberOfElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        foundTournamentsPage.content
                .forEach { Assert.assertEquals(TournamentStatus.FINISHED, it.status) }
    }

    @Test
    fun `should return one page finished user's tournament in which user participates and information about two available pages`() {
        // given
        val pageRequest = PageRequest(1, 4)

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getFinishedTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(5, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.numberOfElements)
        Assert.assertEquals(2, foundTournamentsPage.totalPages)
        Assert.assertFalse(foundTournamentsPage.isFirst)
        foundTournamentsPage.content
                .forEach { Assert.assertEquals(TournamentStatus.FINISHED, it.status) }
    }

    @Test
    fun `should return all finished user's tournaments in which user participates sorted descending by name`() {
        // given
        val pageRequest = PageRequest(0, 10, Sort(Sort.Direction.DESC, "name"))

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getFinishedTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(5, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)

        Assert.assertTrue(foundTournamentsPage.content[0].name > foundTournamentsPage.content[1].name)
        Assert.assertTrue(foundTournamentsPage.content[1].name > foundTournamentsPage.content[2].name)
        Assert.assertTrue(foundTournamentsPage.content[0].name > foundTournamentsPage.content[3].name)
        Assert.assertTrue(foundTournamentsPage.content[3].name > foundTournamentsPage.content[4].name)
        foundTournamentsPage.content
                .forEach { Assert.assertEquals(TournamentStatus.FINISHED, it.status) }
    }

    @Test
    fun `should return all finished user's tournaments in which user participates in two pages sorted ascending by name`() {
        // given
        val pageRequest = PageRequest(0, 10, Sort("name"))

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getFinishedTournamentsInWhichUserParticipates(userId, pageRequest, null)

        // then
        Assert.assertEquals(5, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertTrue(foundTournamentsPage.content[0].name < foundTournamentsPage.content[1].name)
        Assert.assertTrue(foundTournamentsPage.content[1].name < foundTournamentsPage.content[2].name)
        Assert.assertTrue(foundTournamentsPage.content[0].name < foundTournamentsPage.content[3].name)
        Assert.assertTrue(foundTournamentsPage.content[3].name < foundTournamentsPage.content[4].name)
        foundTournamentsPage.content
                .forEach { Assert.assertEquals(TournamentStatus.FINISHED, it.status) }
        foundTournamentsPage.content
                .forEach { Assert.assertEquals(TournamentStatus.FINISHED, it.status) }
    }

    @Test
    fun `should return one finished user's tournaments in which user participates by query`() {
        // given
        val pageRequest = PageRequest(0, 10)
        val tournamentToFindName = "dolore Lorem non commodo cupidatat non"
        val tournamentToFindDescription = "Occaecat deserunt occaecat consequat pariatur consectetur qui elit veniam nostrud veniam magna ex duis."
        val query = tournamentToFindDescription.substring(5)

        // when
        val foundTournamentsPage = tournamentPreviewsFetcher.getFinishedTournamentsInWhichUserParticipates(userId, pageRequest, query)

        // then
        Assert.assertEquals(1, foundTournamentsPage.totalElements)
        Assert.assertEquals(1, foundTournamentsPage.totalPages)
        Assert.assertEquals(tournamentToFindName, foundTournamentsPage.content[0].name)
        Assert.assertEquals(tournamentToFindDescription, foundTournamentsPage.content[0].description)
        foundTournamentsPage.content
                .forEach { Assert.assertEquals(TournamentStatus.FINISHED, it.status) }
    }

    inner class MockAuthModule : AuthModuleInterface {
        override fun getToken() = throw UnsupportedOperationException("This mock shouldn't call this method")

        override fun getUser(token: String) = throw UnsupportedOperationException("This mock shouldn't call this method")

        override fun getUserFromRequest(user: User) = throw UnsupportedOperationException("This mock shouldn't call this method")

        override fun getUserNameById(id: Long) = "Test Owner"
    }
}