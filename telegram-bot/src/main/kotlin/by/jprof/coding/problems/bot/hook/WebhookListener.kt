package by.jprof.coding.problems.bot.hook

import by.jprof.coding.problems.bot.service.TgUpdateHandlingService
import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/")
class WebhookListener(private val tgUpdateHandlingService: TgUpdateHandlingService) {

    @RequestMapping(method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun processUpdate(update: Update) : Mono<String> {
        tgUpdateHandlingService.handleUpdate(update);
        return Mono.just("{}")
    }

}