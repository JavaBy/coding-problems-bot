package by.jprof.coding.problems.bot.domain

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.util.*


enum class Platform(val platform: String) {
    LEETCODE("Leetcode")
}

enum class Messenger(val messenger: String) {
    TELEGRAM("Telegram")
}

@Table
class Problem(
    @Id @JvmField var id: String?,
    val link: String?,
    val title: String?,
    val acceptance: String?,
    val difficulty: String?,
    val platform: String? = Platform.LEETCODE.platform
) : Persistable<String> {
    override fun getId(): String ? = id

    override fun isNew(): Boolean {
        val new = id?.isEmpty() ?: true
        id = if (new) UUID.randomUUID().toString() else id
        return new
    }

}

class Chat(
    val id: String,
    val messenger: String = Messenger.TELEGRAM.messenger
)
