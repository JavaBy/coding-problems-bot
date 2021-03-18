package by.jprof.coding.problems.bot.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


class Platforms {
    companion object {
        const val LEETCODE = "Leetcode"
    }
}

@Table
data class Problem(
    @Id
    val id: String,
    val link: String,
    val title: String,
    val acceptance: Float?,
    val difficulty: String,
    val platform: String = Platforms.LEETCODE
)
