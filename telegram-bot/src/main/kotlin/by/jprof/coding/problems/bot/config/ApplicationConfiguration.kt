package by.jprof.coding.problems.bot.config

import dev.inmo.tgbotapi.bot.Ktor.telegramBot
import dev.inmo.tgbotapi.bot.TelegramBot
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableR2dbcRepositories
@EnableScheduling
class ApplicationConfiguration : AbstractR2dbcConfiguration() {
    companion object {
        val BOT_TOKEN = System.getenv("BOT_TOKEN")!!
    }
    @Bean
    override fun connectionFactory() : ConnectionFactory {
        return ConnectionFactories.get(System.getenv("DB_URL"))
    }

    @Bean
    fun tgBot() : TelegramBot {
        return telegramBot(BOT_TOKEN)
    }
}

