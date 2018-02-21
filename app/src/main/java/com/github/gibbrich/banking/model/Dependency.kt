package com.github.gibbrich.banking.model

/**
 * Created by Dvurechenskiyi on 21.02.2018.
 */
abstract class Dependency(val field: Field, val predicate: RegexPredicate)
{
    abstract fun validate(): Boolean
}

//class SpinnerDependency(field: SpinnerField, predicate: RegexPredicate): Dependency(field, predicate)
//{
//    override fun validate(): Boolean
//    {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//}