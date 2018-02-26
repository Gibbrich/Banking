package com.github.gibbrich.banking.viewModel.utils

import android.support.annotation.StringRes

/**
 * Created by Артур on 24.02.2018.
 */
abstract class Validator(
        open val predicate: Predicate,
        val message: String
)
{
    abstract fun validate(value: String): Boolean
}

class RegexValidator(
        override val predicate: RegexPredicate,
        message: String
): Validator(predicate, message)
{
    override fun validate(value: String) = predicate.validate(value)
}