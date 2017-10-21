package pl.cyganki.tournament.service

import mu.KLogging
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import pl.cyganki.tournament.exception.InvalidTournamentIdException
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.utils.modules.AuthModuleInterface
import pl.cyganki.utils.security.dto.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class MailService(
        private val javaMailSender: JavaMailSender,
        private val templateEngine: TemplateEngine,
        private val tournamentRepository: TournamentRepository,
        private val authModuleInterface: AuthModuleInterface
) {

    fun sendFinishedTournamentEmail(tournamentId: String) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val usersNames = authModuleInterface.getUserNamesByIds(tournament.joinedUsersIds)
        val usersEmails = authModuleInterface.getEmailsByIds(tournament.joinedUsersIds)

        usersNames.forEachIndexed { index, userName ->
            try {
                javaMailSender.send({
                    MimeMessageHelper(it).apply {
                        setTo(usersEmails[index])
                        setSubject("e-Arbiter - koniec turnieju ${tournament.name}")
                        setText(buildFinishedTournamentEmail(tournament.name, userName), true)
                    }
                })
            } catch (e: MailException) {
                logger.warn { e.message }
            }
        }
    }

    fun sendExtendTournamentDeadlineEmail(tournamentId: String) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val usersNames = authModuleInterface.getUserNamesByIds(tournament.joinedUsersIds)
        val usersEmails = authModuleInterface.getEmailsByIds(tournament.joinedUsersIds)

        usersNames.forEachIndexed { index, userName ->
            try {
                javaMailSender.send({
                    MimeMessageHelper(it).apply {
                        setTo(usersEmails[index])
                        setSubject("e-Arbiter - nowa data końcowa turnieju ${tournament.name}")
                        setText(buildExtendTournamentEmail(tournament.name, userName, tournament.endDate), true)
                    }
                })
            } catch (e: MailException) {
                logger.warn { e.message }
            }
        }
    }

    fun sendRemovedUserEmail(tournamentId: String, userId: Long) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val userName = authModuleInterface.getUserNameById(userId)
        val userEmail = authModuleInterface.getEmailById(userId)

        try {
            javaMailSender.send({
                MimeMessageHelper(it).apply {
                    setTo(userEmail)
                    setSubject("e-Arbiter - zostałeś usunięty z turnieju ${tournament.name}")
                    setText(buildRemovedUserEmail(tournament.name, userName), true)
                }
            })
        } catch (e: MailException) {
            logger.warn { e.message }
        }
    }

    fun sendAdminBroadcastEmail(user: User, subject: String, message: String) {

        if (!user.roles.map { it.name.toLowerCase() }.contains("admin")) {
            throw SecurityException("User ${user.name} cannot send an admin email!")
        }

        val usersNames = authModuleInterface.getAllUserNames()
        val usersEmails = authModuleInterface.getAllUsersEmails()

        usersNames.forEachIndexed { index, userName ->
            try {
                javaMailSender.send({
                    MimeMessageHelper(it).apply {
                        setTo(usersEmails[index])
                        setSubject(subject)
                        setText(buildAdminBroadcastEmail(userName, message), true)
                    }
                })
            } catch (e: MailException) {
                logger.warn { e.message }
            }
        }
    }

    private fun buildFinishedTournamentEmail(tournamentName: String, userName: String) =
            Context().run {
                setVariable("tournamentName", tournamentName)
                setVariable("userName", userName)
                templateEngine.process(TemplateName.finishedTournament, this)
            }


    private fun buildExtendTournamentEmail(tournamentName: String, userName: String, newDeadline: LocalDateTime) =
            Context().run {
                setVariable("tournamentName", tournamentName)
                setVariable("userName", userName)
                setVariable("newDeadline", parseDate(newDeadline))
                templateEngine.process(TemplateName.extendTournament, this)

            }

    private fun buildRemovedUserEmail(tournamentName: String, userName: String) =
            Context().run {
                setVariable("tournamentName", tournamentName)
                setVariable("userName", userName)
                templateEngine.process(TemplateName.removedUser, this)
            }

    private fun buildAdminBroadcastEmail(userName: String, message: String) =
            Context().run {
                setVariable("userName", userName)
                setVariable("message", message)
                templateEngine.process(TemplateName.adminBroadcast, this)
            }

    private fun parseDate(date: LocalDateTime) = with(DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd")) { date.format(this) }

    private object TemplateName {
        val finishedTournament = "FinishedTournamentEmailTemplate"
        val extendTournament = "ExtendTournamentEmailTemplate"
        val removedUser = "RemovedUserEmailTemplate"
        val adminBroadcast = "AdminBroadcastEmailTemplate"
    }

    companion object : KLogging()
}