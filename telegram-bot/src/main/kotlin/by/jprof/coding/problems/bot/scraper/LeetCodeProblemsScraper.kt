package by.jprof.coding.problems.bot.scraper

import by.jprof.coding.problems.bot.domain.Problem
import com.codeborne.selenide.Selenide.closeWebDriver
import com.codeborne.selenide.Selenide.open
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter
import org.openqa.selenium.chrome.ChromeDriverService
import org.springframework.stereotype.Component
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.streams.toList
import com.codeborne.selenide.Selenide.`$$` as findAll
import com.codeborne.selenide.Selenide.`$` as find

val htmlToMarkdownConverter = FlexmarkHtmlConverter.builder().build()

@Component
class LeetCodeProblemsScraper {
    companion object {
        const val BASE_URL = "https://leetcode.com/problemset/algorithms"
    }

    fun scrapeAllLeetCodeProblems() : List<Problem> = try {
        configureBrowser()

        open(BASE_URL)

        find("span.row-selector select").selectOptionContainingText("all") // show all problems

        val rows = findAll("#question-app table tbody.reactable-data tr")

        rows.stream().map { row ->
            val columns = row.findAll("td")
            val anchorTag = columns[2].find("a")
            Problem(
                id = "",
                title = anchorTag.ownText,
                link = anchorTag.attr("href")!!,
                acceptance = columns[4].ownText,
                difficulty = columns[5].find("span").ownText
            )
        }.toList()
    } finally {
        closeWebDriver()
    }

    private fun configureBrowser() {
        System.setProperty("selenide.headless", "true")
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true")
        Logger.getLogger("org.openqa.selenium").level = Level.OFF
    }
}



