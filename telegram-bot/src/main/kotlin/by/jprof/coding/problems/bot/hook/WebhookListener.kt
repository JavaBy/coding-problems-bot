package by.jprof.coding.problems.bot.hook

import by.jprof.coding.problems.bot.service.TgUpdateHandlingService
import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class WebhookListener(private val tgUpdateHandlingService: TgUpdateHandlingService) {
    companion object {
        private val EMPTY_JSON_BODY = "{}"
    }

    @PostMapping(produces = [APPLICATION_JSON_VALUE], consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun processUpdate(update: Update) : String {
        tgUpdateHandlingService.handleUpdate(update)
        return EMPTY_JSON_BODY
    }

}