package com.github.gibbrich.banking.model

import java.util.regex.Pattern

/**
 * Created by Dvurechenskiyi on 20.02.2018.
 */
abstract class Predicate(val type: PredicateType)
{
    abstract fun validate(data: String): Boolean
}

enum class PredicateType
{
    REGEX;
}

class RegexPredicate(private val pattern: String): Predicate(PredicateType.REGEX)
{
    private val matcher: Pattern = Pattern.compile(pattern)

    override fun validate(data: String) = matcher.matcher(data).matches()
}