package com.github.gibbrich.banking.model

/**
 * Created by Артур on 24.02.2018.
 */
abstract class Condition(open val predicate: Predicate)

class RegexCondition(
        override val predicate: RegexPredicate
): Condition(predicate)
{
    fun validate(value: String): Boolean
    {
        return predicate.validate(value)
    }
}