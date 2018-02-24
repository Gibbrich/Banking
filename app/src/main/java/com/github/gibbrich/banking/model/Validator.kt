package com.github.gibbrich.banking.model

/**
 * Created by Артур on 24.02.2018.
 */
abstract class Validator(open val predicate: Predicate, val message: String)

class RegexValidator(override val predicate: RegexPredicate, message: String): Validator(predicate, message)