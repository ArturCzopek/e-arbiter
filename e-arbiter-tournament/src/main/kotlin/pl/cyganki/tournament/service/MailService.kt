package pl.cyganki.tournament.service

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
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

    fun sendFinishedTournamentEmail(tournamentId: String) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val users = authModuleInterface.getUserNamesAndEmailsByIds(tournament.joinedUsersIds.toTypedArray())

        users.forEach { user ->
            launch(CommonPool) {
                try {
                    javaMailSender.send({
                        MimeMessageHelper(it, true).apply {
                            setTo(user.email)
                            setSubject("e-Arbiter - koniec turnieju ${tournament.name}")
                            setText(buildFinishedTournamentEmail(tournament.name, user.name), true)
                            addInline(VarName.logo, getLogoImageAsByteArrayResource(), Logo.contentType)
                        }
                    })

                    logger.debug { "Sent email ${TemplateName.finishedTournament} to ${user.name} on mail ${user.email}" }
                } catch (e: MailException) {
                    logger.warn { e.message }
                    throw object : MailException("Cannot send email ${TemplateName.finishedTournament} to ${user.name}: ${e.message}") {}
                }
            }
        }
    }

    fun sendExtendedTournamentDeadlineEmail(tournamentId: String) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val users = authModuleInterface.getUserNamesAndEmailsByIds(tournament.joinedUsersIds.toTypedArray())

        users.forEach { user ->
            launch(CommonPool) {
                try {
                    javaMailSender.send({
                        MimeMessageHelper(it, true, "UTF-8").apply {
                            setTo(user.email)
                            setSubject("e-Arbiter - nowa data końcowa turnieju ${tournament.name}")
                            setText(buildExtendTournamentEmail(tournament.name, user.name, tournament.endDate), true)
                            addInline(VarName.logo, getLogoImageAsByteArrayResource(), Logo.contentType)
                        }
                    })

                    logger.debug { "Sent email ${TemplateName.extendedTournament} to ${user.name} on mail ${user.email}" }
                } catch (e: MailException) {
                    logger.warn { e.message }
                    throw object : MailException("Cannot send email ${TemplateName.extendedTournament} to ${user.name}: ${e.message}") {}
                }
            }
        }
    }

    fun sendActivatedTournamentEmail(tournamentId: String) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val user = authModuleInterface.getUserNamesAndEmailsByIds(tournament.joinedUsersIds.toTypedArray())[0]    // only one result for one user
        launch(CommonPool) {
            try {
                javaMailSender.send({
                    MimeMessageHelper(it, true).apply {
                        setTo(user.email)
                        setSubject("e-Arbiter - aktywacja turnieju ${tournament.name}")
                        setText(buildActivateTournamentEmail(tournament.name, user.name, tournament.endDate), true)
                        addInline(VarName.logo, getLogoImageAsByteArrayResource(), Logo.contentType)
                    }
                })

                logger.debug { "Sent email ${TemplateName.activatedTournament} to ${user.name} on mail ${user.email}" }
            } catch (e: MailException) {
                logger.warn { e.message }
                throw object : MailException("Cannot send email ${TemplateName.activatedTournament} to ${user.name}: ${e.message}") {}
            }
        }
    }

    fun sendRemovedUserFromTournamentEmail(tournamentId: String, userId: Long) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val user = authModuleInterface.getUserNamesAndEmailsByIds(arrayOf(userId))[0]    // only one result for one user

        launch(CommonPool) {
            try {
                javaMailSender.send({
                    MimeMessageHelper(it, true).apply {
                        setTo(user.email)
                        setSubject("e-Arbiter - zostałeś usunięty z turnieju ${tournament.name}")
                        setText(buildRemovedUserFromTournamentEmail(tournament.name, user.name), true)
                        addInline(VarName.logo, getLogoImageAsByteArrayResource(), Logo.contentType)
                    }
                })

                logger.debug { "Sent email ${TemplateName.removedUserFromTournament} to ${user.name} on mail ${user.email}" }
            } catch (e: MailException) {
                logger.warn { e.message }
                throw object : MailException("Cannot send email ${TemplateName.removedUserFromTournament} to ${user.name}: ${e.message}") {}
            }
        }
    }

    fun sendJoinedToTournamentEmail(tournamentId: String, userId: Long) {
        val tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        val user = authModuleInterface.getUserNamesAndEmailsByIds(arrayOf(userId))[0]    // only one result for one user

        launch(CommonPool) {
            try {
                javaMailSender.send({
                    MimeMessageHelper(it, true).apply {
                        setTo(user.email)
                        setSubject("e-Arbiter - dołączyłeś do turnieju ${tournament.name}")
                        setText(buildJoinedToTournamentEmail(tournament.name, user.name, tournament.endDate), true)
                        addInline(VarName.logo, getLogoImageAsByteArrayResource(), Logo.contentType)
                    }
                })

                logger.debug { "Sent email ${TemplateName.joinedToTournament} to ${user.name} on mail ${user.email}" }
            } catch (e: MailException) {
                logger.warn { e.message }
                throw object : MailException("Cannot send email ${TemplateName.joinedToTournament} to ${user.name}: ${e.message}") {}
            }
        }
    }

    fun sendAdminBroadcastEmail(userSender: User, subject: String, message: String) {

        if (!userSender.roles.map { it.name.toLowerCase() }.contains("admin")) {
            throw SecurityException("User ${userSender.name} cannot send an admin email!")
        }

        val users = authModuleInterface.getAllUserNamesAndEmails()

        users.forEach { user ->
            launch(CommonPool) {
                try {
                    javaMailSender.send({
                        MimeMessageHelper(it, true).apply {
                            setTo(user.email)
                            setSubject(subject)
                            setText(buildAdminBroadcastEmail(user.name, message), true)
                            addInline(VarName.logo, getLogoImageAsByteArrayResource(), Logo.contentType)
                        }
                    })

                    logger.debug { "Sent email ${TemplateName.adminBroadcast} to ${user.name} on mail ${user.email}" }
                } catch (e: MailException) {
                    logger.warn { e.message }
                    throw object : MailException("Cannot send email ${TemplateName.adminBroadcast} to ${user.name}: ${e.message}") {}
                }
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
                templateEngine.process(TemplateName.extendedTournament, this)
            }

    private fun buildActivateTournamentEmail(tournamentName: String, userName: String, deadline: LocalDateTime) =
            Context().run {
                setVariable(VarName.tournamentName, tournamentName)
                setVariable(VarName.userName, userName)
                setVariable(VarName.logo, VarName.logo)
                setVariable(VarName.deadline, parseDate(deadline))
                templateEngine.process(TemplateName.activatedTournament, this)
            }

    private fun buildRemovedUserFromTournamentEmail(tournamentName: String, userName: String) =
            Context().run {
                setVariable(VarName.tournamentName, tournamentName)
                setVariable(VarName.userName, userName)
                setVariable(VarName.logo, VarName.logo)
                templateEngine.process(TemplateName.removedUserFromTournament, this)
            }

    private fun buildJoinedToTournamentEmail(tournamentName: String, userName: String, deadline: LocalDateTime) =
            Context().run {
                setVariable(VarName.tournamentName, tournamentName)
                setVariable(VarName.userName, userName)
                setVariable(VarName.logo, VarName.logo)
                setVariable(VarName.deadline, parseDate(deadline))
                templateEngine.process(TemplateName.joinedToTournament, this)
            }

    private fun buildAdminBroadcastEmail(userName: String, message: String) =
            Context().run {
                setVariable(VarName.userName, userName)
                setVariable(VarName.message, message)
                setVariable(VarName.logo, VarName.logo)
                templateEngine.process(TemplateName.adminBroadcast, this)
            }

    private fun getLogoImageAsByteArrayResource() = ByteArrayResource(javaClass.classLoader.getResourceAsStream(Logo.path).readBytes())

    private fun parseDate(date: LocalDateTime) = with(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) { date.format(this) }

    private object Logo {
        val path = "static/img/logo.jpg"
        val contentType = "image/jpeg"
    }

    private object VarName {
        val tournamentName = "tournamentName"
        val userName = "userName"
        val message = "message"
        val logo = "logo"
        val deadline = "deadline"
    }

    private object TemplateName {
        val finishedTournament = "FinishedTournamentEmailTemplate"
        val extendedTournament = "ExtendedTournamentEmailTemplate"
        val activatedTournament = "ActivatedTournamentEmailTemplate"
        val removedUserFromTournament = "RemovedUserFromTournamentEmailTemplate"
        val joinedToTournament = "JoinedToTournamentEmailTemplate"
        val adminBroadcast = "AdminBroadcastEmailTemplate"
    }

    companion object : KLogging()
}