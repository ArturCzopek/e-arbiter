package pl.cyganki.tournament.controller.admin

import io.swagger.annotations.ApiOperation
import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.tournament.model.request.AdminMailRequest
import pl.cyganki.tournament.service.MailService
import pl.cyganki.utils.security.dto.User

@RestController
@RequestMapping("/admin")
class AdminController(private val mailService: MailService) {

    @PostMapping("/send-email")
    @ApiOperation("Endpoint for sending email to all users by admin. Returns 200 if request has been received. Check logs to see if mail sending has failed")
    fun sendAdminBroadcastMail(user: User, @RequestBody adminMailRequest: AdminMailRequest): ResponseEntity<String> {
        mailService.sendAdminBroadcastEmail(user, adminMailRequest.subject, adminMailRequest.message)
        logger.info { "Request for sending emails to all users with subject ${adminMailRequest.subject} has been received" }
        return ResponseEntity.ok("Request for sending emails to all users with subject ${adminMailRequest.subject} has been received")
    }

    companion object: KLogging()
}