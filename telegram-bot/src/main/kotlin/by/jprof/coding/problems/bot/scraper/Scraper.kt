package by.jprof.coding.problems.bot.scraper

import by.jprof.coding.problems.bot.domain.Problem
import by.jprof.coding.problems.bot.domain.Tag
import com.codeborne.selenide.Selenide.open
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter
import me.tongfei.progressbar.ProgressBar
import org.openqa.selenium.chrome.ChromeDriverService
import org.springframework.stereotype.Component
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.streams.toList
import com.codeborne.selenide.Selenide.`$$` as findAll
import com.codeborne.selenide.Selenide.`$` as find

val htmlToMarkdownConverter = FlexmarkHtmlConverter.builder().build()

@Component
class Scraper {
    fun scrapeLeetCodeProblems() : List<Problem> {
        configureBrowser()

        open("https://leetcode.com/problemset/algorithms")

        find("span.row-selector select").selectOptionContainingText("all") // show all problems

        val rows = findAll("#question-app table tbody.reactable-data tr")

        val emptyProblems = ProgressBar.wrap(rows.stream(), "Populating problems list:").map { row ->
            val columns = row.findAll("td")
            val a = columns[2].find("a")
            Problem(
                number = Integer.parseInt(columns[1].ownText),
                title = a.ownText,
                link = a.attr("href")!!,
                acceptance = columns[4].ownText,
                difficulty = columns[5].ownText
            )
        }.toList()

        return ProgressBar.wrap(emptyProblems, "Populating problems details:").map { problem ->
            open(problem.link)
            val questionContent = find("div[class*='question-content']")
            if (questionContent.exists()) {
                val contentHtml = questionContent.text
                val content = htmlToMarkdownConverter.convert(contentHtml)
                val tags = findAll("a[class^='topic']").stream().map {
                    Tag(link = it.attr("href")!!, title = htmlToMarkdownConverter.convert(it.text))
                }.toList()
                problem.copy(question = content, tags = tags)
            } else {
                problem
            }
        }
    }

    private fun configureBrowser() {
        System.setProperty("selenide.headless", "true")
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true")
        Logger.getLogger("org.openqa.selenium").level = Level.OFF
    }
}



