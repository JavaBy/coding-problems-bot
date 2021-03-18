package by.jprof.coding.problems.bot.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Problem(
    val number: Int,
    val link: String,
    @Id
    val id: String = link.split("/").last(),
    val title: String,
    val acceptance: String,
    val difficulty: String,
    val question: String = "",
    val tags: List<Tag> = emptyList(),
    val hints: List<Hint> = emptyList()
)

data class Tag(val link : String, val title: String)

data class Hint(val id: String, val content: String)
