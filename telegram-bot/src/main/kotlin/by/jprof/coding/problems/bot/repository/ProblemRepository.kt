package by.jprof.coding.problems.bot.repository

import by.jprof.coding.problems.bot.domain.Problem
import by.jprof.coding.problems.bot.projection.ProblemLink
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ProblemRepository : CoroutineCrudRepository<Problem, String> {
    fun findAllProjectedBy() : Flow<ProblemLink>
}