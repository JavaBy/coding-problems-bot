package by.jprof.coding.problems.bot.repository

import by.jprof.coding.problems.bot.domain.Chat
import by.jprof.coding.problems.bot.domain.Problem
import by.jprof.coding.problems.bot.projection.ProblemLink
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProblemRepository : CoroutineCrudRepository<Problem, String> {
    fun findAllProjectedBy() : Flow<ProblemLink>
}

@Repository
interface ChatRepository : CoroutineCrudRepository<Chat, String>