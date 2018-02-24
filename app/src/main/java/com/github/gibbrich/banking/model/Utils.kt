package com.github.gibbrich.banking.model

/**
 * Created by Артур on 25.02.2018.
 */

@FunctionalInterface
interface Callback<T>
{
    fun run(param: T)
}