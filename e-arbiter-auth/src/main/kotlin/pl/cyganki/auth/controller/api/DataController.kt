package pl.cyganki.auth.controller.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
class DataController {

    @Value("\${e-arbiter.clientUrl}")
    lateinit var clientUrl: String

    @GetMapping("/clientUrl")
    @ApiOperation("Returns a web client url. It is used for redirecting from server to client")
    fun fetchClientUrl(): String = clientUrl
}
