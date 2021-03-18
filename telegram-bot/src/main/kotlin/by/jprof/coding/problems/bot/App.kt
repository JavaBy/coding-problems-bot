package by.jprof.coding.problems.bot

import by.jprof.coding.problems.bot.repository.ProblemRepository
import by.jprof.coding.problems.bot.scraper.LeetCodeProblemsScraper
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class App(
    private val scraper: LeetCodeProblemsScraper,
    private val problemRepository: ProblemRepository
) : CommandLineRunner {



    override fun run(vararg args: String?) {
        problemRepository.saveAll(scraper.scrapeAllLeetCodeProblems())
        TODO("finish")
    }

}


fun main(args: Array<String>) {
    runApplication<App>(*args)
}