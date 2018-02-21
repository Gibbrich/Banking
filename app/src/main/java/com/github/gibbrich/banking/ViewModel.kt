package com.github.gibbrich.banking


import android.content.Context
import android.databinding.BaseObservable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.gibbrich.banking.model.AccountType
import com.github.gibbrich.banking.model.RegexPredicate
import com.github.gibbrich.banking.model.SpinnerField
import com.github.gibbrich.banking.model.TextField

/**
 * Created by Dvurechenskiyi on 20.02.2018.
 */
class ViewModel(private val context: Context)
{
    val accountType: SpinnerField<AccountType>
    val accountTypeHandler: Handler

//    val cardNumber: TextField

    init
    {
        val accountTypeWidget = RadioWidget(context, R.string.account_type_title, R.string.account_type_prompt, AccountType.values())
        val accountTypePredicate = RegexPredicate("^2\$|^5\$")
        accountType = SpinnerField(accountTypeWidget, accountTypePredicate)

        accountTypeHandler = Handler()

//        val cardNumberWidget = TextWidget(context, R.string.card_number_title, R.string.card_number_prompt)
//        cardNumber = TextField(cardNumberWidget)
    }
}

class Handler: BaseObservable(), AdapterView.OnItemSelectedListener
{
    var selectedAccountType: AccountType = AccountType.ACCOUNT
        set(value)
        {
            field = value
            notifyChange()
        }

    override fun onNothingSelected(parent: AdapterView<*>)
    {
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)
    {
        selectedAccountType = (parent.adapter as ArrayAdapter<AccountType>).getItem(position)
    }
}