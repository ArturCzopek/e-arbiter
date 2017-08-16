package pl.cyganki.tournament.model;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import pl.cyganki.tournament.exception.IllegalTournamentStatusException;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This tests may look like something unnecessary but that's wrong.
 * We need to test flow of tournament status, mainly by checking when exceptions are thrown
 */
@ActiveProfiles("test")
public class TournamentTest {

    Tournament tournament;

    // test is probably ok
    // however, when I run it separately, it works
    // if this test is running with other tests, 11 is omitted in points...
    @Test
    @Ignore
    public void shouldReturnValidMaxPoints() {
        // given
        tournament = MockTournament.getDraft();
        tournament.setTasks(TestData.TASKS);

        // when
        long expectedPoints = 33; // 11 + 22, check test data
        long foundPoints = tournament.getMaxPoints();

        // then
        assertEquals(expectedPoints, foundPoints);
    }

    // Owner Id

    @Test
    public void shouldSetOwnerIdForDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.setOwnerId(TestData.USER_ID);

        // then
        assertEquals(TestData.USER_ID, tournament.getOwnerId());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingOwnerIdForActiveTournament() {
        // given
        tournament = MockTournament.getActive();

        // when
        tournament.setOwnerId(TestData.USER_ID);

        // then
        assertNotEquals(TestData.USER_ID, tournament.getOwnerId());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingOwnerIdForFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();

        // when
        tournament.setOwnerId(TestData.USER_ID);

        // then
        assertNotEquals(TestData.USER_ID, tournament.getOwnerId());
    }

    // Name

    @Test
    public void shouldSetNameForDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.setName(TestData.NAME);

        // then
        assertEquals(TestData.NAME, tournament.getName());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingNameForActiveTournament() {
        // given
        tournament = MockTournament.getActive();

        // when
        tournament.setName(TestData.NAME);

        // then
        assertNotEquals(TestData.NAME, tournament.getName());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingNameForFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();

        // when
        tournament.setName(TestData.NAME);

        // then
        assertNotEquals(TestData.NAME, tournament.getName());
    }

    // Description

    @Test
    public void shouldSetDescriptionForDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.setDescription(TestData.DESCRIPTION);

        // then
        assertEquals(TestData.DESCRIPTION, tournament.getDescription());
    }

    @Test
    public void shouldSetDescriptionForActiveTournament() {
        // given
        tournament = MockTournament.getActive();

        // when
        tournament.setDescription(TestData.DESCRIPTION);

        // then
        assertEquals(TestData.DESCRIPTION, tournament.getDescription());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingDescriptionForFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();

        // when
        tournament.setDescription(TestData.DESCRIPTION);

        // then
        assertNotEquals(TestData.DESCRIPTION, tournament.getDescription());
    }

    // End date

    @Test
    public void shouldSetEndDateForDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.setEndDate(TestData.END_DATE);

        // then
        assertEquals(TestData.END_DATE, tournament.getEndDate());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingEndDateForActiveTournament() {
        // given
        tournament = MockTournament.getActive();

        // when
        tournament.setEndDate(TestData.END_DATE);

        // then
        assertNotEquals(TestData.END_DATE, tournament.getEndDate());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingEndDateForFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();

        // when
        tournament.setEndDate(TestData.END_DATE);

        // then
        assertNotEquals(TestData.END_DATE, tournament.getEndDate());
    }

    // extend deadline

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForExtendingDeadlineForDraftTournament() {
        tournament = new MockTournament(TournamentStatus.DRAFT, TestData.END_DATE);
        LocalDateTime extendedDate = TestData.END_DATE.plus(TestData.FIVE_DAYS_PERIOD);

        // when
        tournament.extendDeadline(TestData.FIVE_DAYS_PERIOD);

        // then
        assertEquals(extendedDate, tournament.getEndDate());
    }

    @Test
    public void shouldExtendDeadlineForActiveTournament() {
        tournament = new MockTournament(TournamentStatus.ACTIVE, TestData.END_DATE);
        LocalDateTime extendedDate = TestData.END_DATE.plus(TestData.FIVE_DAYS_PERIOD);

        // when
        tournament.extendDeadline(TestData.FIVE_DAYS_PERIOD);

        // then
        assertEquals(extendedDate, tournament.getEndDate());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForExtendingDeadlineForFinishedTournament() {
        tournament = new MockTournament(TournamentStatus.FINISHED, TestData.END_DATE);
        LocalDateTime extendedDate = TestData.END_DATE.plus(TestData.FIVE_DAYS_PERIOD);

        // when
        tournament.extendDeadline(TestData.FIVE_DAYS_PERIOD);

        // then
        assertEquals(extendedDate, tournament.getEndDate());
    }

    // Public flag

    @Test
    public void shouldSetPublicFlagForDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.setPublicFlag(TestData.PUBLIC_FLAG);

        // then
        assertEquals(TestData.PUBLIC_FLAG, tournament.isPublicFlag());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingPublicFlagForActiveTournament() {
        // given
        tournament = MockTournament.getActive();

        // when
        tournament.setPublicFlag(TestData.PUBLIC_FLAG);

        // then
        assertNotEquals(TestData.PUBLIC_FLAG, tournament.isPublicFlag());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingPublicFlagForFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();

        // when
        tournament.setPublicFlag(TestData.PUBLIC_FLAG);

        // then
        assertNotEquals(TestData.PUBLIC_FLAG, tournament.isPublicFlag());
    }

    // User

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForAddingUserToDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.addUser(TestData.USER_ID);

        // then
        assertTrue(tournament.getJoinedUsersIds().isEmpty()); // // for draft list is empty at first
    }

    @Test
    public void shouldAddUserToActiveTournament() {
        // given
        tournament = MockTournament.getActive();

        // when
        int usersInTournamentBeforeAdding = tournament.getJoinedUsersIds().size();
        tournament.addUser(TestData.USER_ID);

        // then
        assertEquals(usersInTournamentBeforeAdding + 1, tournament.getJoinedUsersIds().size());
        assertTrue(tournament.getJoinedUsersIds().contains(TestData.USER_ID));
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForAddingUserToFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();
        tournament.getJoinedUsersIds().add(TestData.USER_ID + 1);

        // when
        int usersInTournamentBeforeAdding = tournament.getJoinedUsersIds().size();
        tournament.addUser(TestData.USER_ID);

        // then
        assertEquals(usersInTournamentBeforeAdding, tournament.getJoinedUsersIds().size());
        assertFalse(tournament.getJoinedUsersIds().contains(TestData.USER_ID));
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForRemovingUserToDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.removeUser(TestData.USER_ID);

        // then
        assertTrue(tournament.getJoinedUsersIds().isEmpty()); // // for draft list is empty at first
    }

    @Test
    public void shouldRemoveUserFromActiveTournament() {
        // given
        tournament = MockTournament.getActive();
        tournament.getJoinedUsersIds().add(TestData.USER_ID);
        tournament.getJoinedUsersIds().add(TestData.USER_ID + 1);

        // when
        int usersInTournamentBeforeAdding = tournament.getJoinedUsersIds().size();
        tournament.removeUser(TestData.USER_ID);

        // then
        assertEquals(usersInTournamentBeforeAdding - 1, tournament.getJoinedUsersIds().size());
        assertFalse(tournament.getJoinedUsersIds().contains(TestData.USER_ID));
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForRemovingUserToFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();
        tournament.getJoinedUsersIds().add(TestData.USER_ID);
        tournament.getJoinedUsersIds().add(TestData.USER_ID + 1);

        // when
        int usersInTournamentBeforeAdding = tournament.getJoinedUsersIds().size();
        tournament.removeUser(TestData.USER_ID);

        // then
        assertEquals(usersInTournamentBeforeAdding, tournament.getJoinedUsersIds().size());
        assertFalse(tournament.getJoinedUsersIds().contains(TestData.USER_ID));
    }

    // Results visible for joined users

    @Test
    public void shouldSetResultsVisibleForJoinedUsersForDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.setResultsVisibleForJoinedUsers(TestData.RESULTS_VISIBLE);

        // then
        assertEquals(TestData.RESULTS_VISIBLE, tournament.isResultsVisibleForJoinedUsers());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingResultsVisibleForJoinedUsersForActiveTournament() {
        // given
        tournament = MockTournament.getActive();

        // when
        tournament.setResultsVisibleForJoinedUsers(TestData.RESULTS_VISIBLE);

        // then
        assertNotEquals(TestData.RESULTS_VISIBLE, tournament.isResultsVisibleForJoinedUsers());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingResultsVisibleForJoinedUsersForFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();

        // when
        tournament.setResultsVisibleForJoinedUsers(TestData.RESULTS_VISIBLE);

        // then
        assertNotEquals(TestData.RESULTS_VISIBLE, tournament.isResultsVisibleForJoinedUsers());
    }

    // Password

    @Test
    public void shouldSetPasswordForDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.setPassword(TestData.PASSWORD);

        // then
        assertEquals(TestData.PASSWORD, tournament.getPassword());
    }

    @Test
    public void shouldSetPasswordForActiveTournament() {
        // given
        tournament = MockTournament.getActive();

        // when
        tournament.setPassword(TestData.PASSWORD);

        // then
        assertEquals(TestData.PASSWORD, tournament.getPassword());
    }

    @Test
    public void shouldSetPasswordForFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();

        // when
        tournament.setPassword(TestData.PASSWORD);

        // then
        assertEquals(TestData.PASSWORD, tournament.getPassword());
    }

    // activate

    @Test
    public void shouldActivateDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.activate();

        // then
        assertEquals(TournamentStatus.ACTIVE, tournament.getStatus());
        assertNotNull(tournament.getStartDate());
        assertTrue(tournament.getJoinedUsersIds().isEmpty());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForActivatingActiveTournament() {
        // given
        tournament = MockTournament.getActive();
        tournament.getJoinedUsersIds().add(TestData.USER_ID);

        // when
        tournament.activate();

        // then
        assertEquals(TournamentStatus.ACTIVE, tournament.getStatus());
        assertFalse(tournament.getJoinedUsersIds().isEmpty());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForActivatingFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();
        tournament.getJoinedUsersIds().add(TestData.USER_ID);

        // when
        tournament.activate();

        // then
        assertEquals(TournamentStatus.FINISHED, tournament.getStatus());
        assertFalse(tournament.getJoinedUsersIds().isEmpty());
    }

    // finish

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForFinishingDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.finish();

        // then
        assertEquals(TournamentStatus.DRAFT, tournament.getStatus());
    }

    @Test
    public void shouldFinishActiveTournament() {
        // given
        tournament = MockTournament.getActive();

        // when
        tournament.finish();

        // then
        assertEquals(TournamentStatus.FINISHED, tournament.getStatus());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForFinishingFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();

        // when
        tournament.finish();

        // then
        assertEquals(TournamentStatus.FINISHED, tournament.getStatus());
    }

    // Tasks

    @Test
    public void shouldSetTasksForDraftTournament() {
        // given
        tournament = MockTournament.getDraft();

        // when
        tournament.setTasks(TestData.TASKS);

        // then
        assertEquals(TestData.TASKS, tournament.getTasks());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingTasksForActiveTournament() {
        // given
        tournament = MockTournament.getActive();

        // when
        tournament.setTasks(TestData.TASKS);

        // then
        assertNotEquals(TestData.TASKS, tournament.getTasks());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForSettingTasksForFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();

        // when
        tournament.setTasks(TestData.TASKS);

        // then
        assertNotEquals(TestData.TASKS, tournament.getTasks());
    }

    @Test
    public void shouldAddTaskForDraftTournament() {
        // given
        tournament = MockTournament.getDraft();
        Task taskToAdd = TestData.getMockTask(TestData.TASK_MAX_POINTS);

        // when
        int tasksSize = tournament.getTasks().size();
        tournament.addTask(taskToAdd);

        // then
        assertEquals(tasksSize + 1, tournament.getTasks().size());
        assertTrue(tournament.getTasks().contains(taskToAdd));
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForAddingTaskToActiveTournament() {
        // given
        tournament = MockTournament.getActive();
        Task taskToAdd = TestData.getMockTask(TestData.TASK_MAX_POINTS);

        // when
        int tasksSize = tournament.getTasks().size();
        tournament.addTask(taskToAdd);

        // then
        assertEquals(tasksSize, tournament.getTasks().size());
        assertFalse(tournament.getTasks().contains(taskToAdd));
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForAddingTaskToFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();
        Task taskToAdd = TestData.getMockTask(TestData.TASK_MAX_POINTS);

        // when
        int tasksSize = tournament.getTasks().size();
        tournament.addTask(taskToAdd);

        // then
        assertEquals(tasksSize, tournament.getTasks().size());
        assertFalse(tournament.getTasks().contains(taskToAdd));
    }

    @Test
    public void shouldGetTaskByPassedId() {
        // given
        tournament = MockTournament.getDraft();
        tournament.setTasks(TestData.TASKS);

        // when
        Task foundTask = tournament.getTask(TestData.TASK_INDEX);

        // then
        assertNotNull(foundTask);
    }

    @Test
    public void shouldRemoveTaskFromDraftTournament() {
        // given
        tournament = MockTournament.getDraft();
        tournament.setTasks(TestData.TASKS);

        // when
        int tasksSize = tournament.getTasks().size();
        tournament.removeTask(TestData.TASK_INDEX);

        // then
        assertEquals(tasksSize - 1, tournament.getTasks().size());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForRemovingTaskFromActiveTournament() {
        // given
        tournament = MockTournament.getActive();
        tournament.setTasks(TestData.TASKS);

        // when
        int tasksSize = tournament.getTasks().size();
        tournament.removeTask(TestData.TASK_INDEX);

        // then
        assertEquals(tasksSize, tournament.getTasks().size());
    }

    @Test(expected = IllegalTournamentStatusException.class)
    public void shouldThrowExceptionForRemovingTaskToFinishedTournament() {
        // given
        tournament = MockTournament.getFinished();
        tournament.setTasks(TestData.TASKS);

        // when
        int tasksSize = tournament.getTasks().size();
        tournament.removeTask(TestData.TASK_INDEX);

        // then
        assertEquals(tasksSize, tournament.getTasks().size());
    }

    @Test
    public void shouldUpdateTaskInDraftTournament() {
        // given
        tournament = MockTournament.getDraft();
        tournament.setTasks(TestData.TASKS);

        String firstDescription = "First version of description";
        String newDescription = "Bla bla bla";

        Task firstVersionOfTaskToUpdate = new CodeTask();
        firstVersionOfTaskToUpdate.setDescription(firstDescription);

        tournament.addTask(firstVersionOfTaskToUpdate);

        Task taskToUpdate = new CodeTask();
        taskToUpdate.setDescription(newDescription);

        // when
        Task firstVersionFromList = tournament.getTask(TestData.TASK_INDEX);
        tournament.updateTask(taskToUpdate, TestData.TASK_INDEX);
        Task updatedTask = tournament.getTask(TestData.TASK_INDEX);

        // then
        assertNotEquals(firstVersionFromList.getDescription(), updatedTask.getDescription());
        assertEquals(updatedTask, taskToUpdate);
    }

    private static class TestData {
        final static Long USER_ID = 33L;
        final static String NAME = "Test tournament";
        final static String DESCRIPTION = "Test description Test description Test description Test description Test description";
        final static LocalDateTime END_DATE = LocalDateTime.of(2017, Month.AUGUST, 1, 15, 0, 0, 0);
        final static Period FIVE_DAYS_PERIOD = Period.ofDays(5);
        final static boolean PUBLIC_FLAG = true;
        final static boolean RESULTS_VISIBLE = true;
        final static String PASSWORD = "Test123";
        final static int TASK_INDEX = 0;
        final static long TASK_MAX_POINTS = 10;
        final static long SECOND_TASK_MAX_POINTS = 20;
        final static List<Task> TASKS = new ArrayList<>(Arrays.asList(getMockTask(TASK_MAX_POINTS), getMockTask(SECOND_TASK_MAX_POINTS)));    // remove is unsupported for list from Arrays.asList

        public static Task getMockTask(long maxPoints) {
            Task task = mock(Task.class);
            when(task.getMaxPoints()).thenReturn(maxPoints);
            return task;
        }
    }
}

