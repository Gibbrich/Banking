package com.github.gibbrich.banking.model

/**
 * Created by Dvurechenskiyi on 20.02.2018.
 */
enum class AccountType(val value: Int, val title: String)
{
    ACCOUNT(2, "Номер счета"),
    CARD(5, "Номер карты");
}