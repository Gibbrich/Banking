package com.github.gibbrich.banking.viewModel.utils

import java.util.regex.Pattern

/**
 * Created by Dvurechenskiyi on 20.02.2018.
 */
abstract class Predicate
{
    abstract fun validate(data: String): Boolean
}

class RegexPredicate(pattern: String): Predicate()
{
    private val matcher: Pattern = Pattern.compile(pattern)

    override fun validate(data: String) = matcher.matcher(data).matches()
}