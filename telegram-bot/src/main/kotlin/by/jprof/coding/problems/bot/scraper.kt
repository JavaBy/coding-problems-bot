package by.jprof.coding.problems.bot

import com.codeborne.selenide.Selenide.`$` as find
import com.codeborne.selenide.Selenide.`$$` as findAll
import com.codeborne.selenide.Selenide.open
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter
import kotlin.streams.toList

val htmlToMarkdownConverter = FlexmarkHtmlConverter.builder().build()

fun scrapeLeetCodeProblems() : List<Problem> {

    System.setProperty("selenide.headless", "true")
    open("https://leetcode.com/problemset/algorithms")

    find("span.row-selector select").selectOptionContainingText("all") // show all problems

    val rows = findAll("#question-app table tbody.reactable-data tr")

    val emptyProblems = rows.stream().map { row ->
        print(".")
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

    return emptyProblems.map { problem ->
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

