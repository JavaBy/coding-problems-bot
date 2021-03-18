package by.jprof.coding.problems.bot.repository

import by.jprof.coding.problems.bot.domain.Problem
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ProblemRepository : CoroutineCrudRepository<Problem, String>