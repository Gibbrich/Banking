package com.github.gibbrich.banking


import com.github.gibbrich.banking.model.AccountType

/**
 * Created by Dvurechenskiyi on 20.02.2018.
 */
class ViewModel
{
    var accountType: AccountType = AccountType.ACCOUNT

    fun isMFOFieldVisible(): Boolean
    {
        return accountType == AccountType.ACCOUNT
    }
}