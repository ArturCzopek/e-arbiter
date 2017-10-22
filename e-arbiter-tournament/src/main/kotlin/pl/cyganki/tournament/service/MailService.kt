package pl.cyganki.tournament.service

import mu.KLogging
import org.springframework.core.io.ByteArrayResource
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

    private val logoImagePath = "static/img/logo.jpg"

    fun sendFinishedTournamentEmail(tournamentId: String) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val usersNames = authModuleInterface.getUserNamesByIds(tournament.joinedUsersIds.toTypedArray())
        val usersEmails = authModuleInterface.getEmailsByIds(tournament.joinedUsersIds.toTypedArray())

        usersNames.forEachIndexed { index, userName ->
            try {
                javaMailSender.send({
                    MimeMessageHelper(it, true).apply {
                        setTo(usersEmails[index])
                        setSubject("e-Arbiter - koniec turnieju ${tournament.name}")
                        setText(buildFinishedTournamentEmail(tournament.name, userName), true)
                        addInline(VarName.logo, getLogoImageAsByteArrayResource(), "image/jpeg")
                    }
                })

                logger.debug { "Sent email ${TemplateName.finishedTournament} to $userName on mail ${usersEmails[index]}" }
            } catch (e: MailException) {
                logger.warn { e.message }
                throw Exception("Cannot send email")
            }
        }
    }

    fun sendExtendTournamentDeadlineEmail(tournamentId: String) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val usersNames = authModuleInterface.getUserNamesByIds(tournament.joinedUsersIds.toTypedArray())
        val usersEmails = authModuleInterface.getEmailsByIds(tournament.joinedUsersIds.toTypedArray())

        usersNames.forEachIndexed { index, userName ->
            try {
                javaMailSender.send({
                    MimeMessageHelper(it, true, "UTF-8").apply {
                        setTo(usersEmails[index])
                        setSubject("e-Arbiter - nowa data końcowa turnieju ${tournament.name}")
                        setText(buildExtendTournamentEmail(tournament.name, userName, tournament.endDate), true)
                        addInline(VarName.logo, getLogoImageAsByteArrayResource(), "image/jpeg")
                    }
                })

                logger.debug { "Sent email ${TemplateName.extendTournament} to $userName on mail ${usersEmails[index]}" }
            } catch (e: MailException) {
                logger.warn { e.message }
                throw Exception("Cannot send email")
            }
        }
    }

    fun sendActivateTournamentEmail(tournamentId: String) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val userName = authModuleInterface.getUserNameById(tournament.ownerId)
        val userEmail = authModuleInterface.getEmailById(tournament.ownerId)

        try {
            javaMailSender.send({
                MimeMessageHelper(it, true).apply {
                    setTo(userEmail)
                    setSubject("e-Arbiter - aktywacja turnieju ${tournament.name}")
                    setText(buildActivateTournamentEmail(tournament.name, userName, tournament.endDate), true)
                    addInline(VarName.logo, getLogoImageAsByteArrayResource(), "image/jpeg")
                }
            })

            logger.debug { "Sent email ${TemplateName.activateTournament} to $userName on mail $userEmail" }
        } catch (e: MailException) {
            logger.warn { e.message }
            throw Exception("Cannot send email")
        }

    }

    fun sendRemovedUserEmail(tournamentId: String, userId: Long) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val userName = authModuleInterface.getUserNameById(userId)
        val userEmail = authModuleInterface.getEmailById(userId)

        try {
            javaMailSender.send({
                MimeMessageHelper(it, true).apply {
                    setTo(userEmail)
                    setSubject("e-Arbiter - zostałeś usunięty z turnieju ${tournament.name}")
                    setText(buildRemovedUserEmail(tournament.name, userName), true)
                    addInline(VarName.logo, getLogoImageAsByteArrayResource(), "image/jpeg")
                }
            })

            logger.debug { "Sent email ${TemplateName.removedUser} to $userName on mail $userEmail" }
        } catch (e: MailException) {
            logger.warn { e.message }
            throw Exception("Cannot send email")
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
                    MimeMessageHelper(it, true).apply {
                        setTo(usersEmails[index])
                        setSubject(subject)
                        setText(buildAdminBroadcastEmail(userName, message), true)
                        addInline(VarName.logo, getLogoImageAsByteArrayResource(), "image/jpeg")
                    }
                })

                logger.debug { "Sent email ${TemplateName.adminBroadcast} to $userName on mail ${usersEmails[index]}" }
            } catch (e: MailException) {
                logger.warn { e.message }
                throw Exception("Cannot send email")
            }
        }
    }

    private fun buildFinishedTournamentEmail(tournamentName: String, userName: String) =
            Context().run {
                setVariable(VarName.tournamentName, tournamentName)
                setVariable(VarName.userName, userName)
                setVariable(VarName.logo, VarName.logo)
                templateEngine.process(TemplateName.finishedTournament, this)
            }

    private fun buildExtendTournamentEmail(tournamentName: String, userName: String, deadline: LocalDateTime) =
            Context().run {
                setVariable(VarName.tournamentName, tournamentName)
                setVariable(VarName.userName, userName)
                setVariable(VarName.logo, VarName.logo)
                setVariable(VarName.deadline, parseDate(deadline))
                templateEngine.process(TemplateName.extendTournament, this)
            }

    private fun buildActivateTournamentEmail(tournamentName: String, userName: String, deadline: LocalDateTime) =
            Context().run {
                setVariable(VarName.tournamentName, tournamentName)
                setVariable(VarName.userName, userName)
                setVariable(VarName.logo, VarName.logo)
                setVariable(VarName.deadline, parseDate(deadline))
                templateEngine.process(TemplateName.activateTournament, this)
            }

    private fun buildRemovedUserEmail(tournamentName: String, userName: String) =
            Context().run {
                setVariable(VarName.tournamentName, tournamentName)
                setVariable(VarName.userName, userName)
                setVariable(VarName.logo, VarName.logo)
                templateEngine.process(TemplateName.removedUser, this)
            }

    private fun buildAdminBroadcastEmail(userName: String, message: String) =
            Context().run {
                setVariable(VarName.userName, userName)
                setVariable(VarName.message, message)
                setVariable(VarName.logo, VarName.logo)
                templateEngine.process(TemplateName.adminBroadcast, this)
            }

    private fun getLogoImageAsByteArrayResource() = ByteArrayResource(javaClass.classLoader.getResourceAsStream(this.logoImagePath).readBytes())

    private fun parseDate(date: LocalDateTime) = with(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm ")) { date.format(this) }

    private object VarName {
        val tournamentName = "tournamentName"
        val userName = "userName"
        val message = "message"
        val logo = "logo"
        val deadline = "deadline"
    }

    private object TemplateName {
        val finishedTournament = "FinishedTournamentEmailTemplate"
        val extendTournament = "ExtendTournamentEmailTemplate"
        val activateTournament = "ActivateTournamentEmailTemplate"
        val removedUser = "RemovedUserEmailTemplate"
        val adminBroadcast = "AdminBroadcastEmailTemplate"
    }

    companion object : KLogging()
}