package com.github.gibbrich.banking.viewModel

import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.github.gibbrich.banking.BR
import com.github.gibbrich.banking.R
import com.github.gibbrich.banking.adapter.EnumArrayAdapter
import com.github.gibbrich.banking.model.*
import com.github.gibbrich.banking.utils.FourDigitCardFormatWatcher
import com.github.gibbrich.banking.view.MainActivityView
import com.github.gibbrich.banking.viewModel.utils.*
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * Created by Dvurechenskiyi on 26.02.2018.
 */
class ViewModel(private val view: MainActivityView) : BaseViewModel()
{
    private val SELECTED_ACCOUNT_TYPE = "SELECTED_ACCOUNT_TYPE"
    private val CARD_NUMBER = "CARD_NUMBER"
    private val ACCOUNT_MFO = "ACCOUNT_MFO"
    private val ACCOUNT_NUMBER = "ACCOUNT_NUMBER"
    private val ACCOUNT_PAYMENT_TYPE = "ACCOUNT_PAYMENT_TYPE"
    private val ACCOUNT_LAST_NAME = "ACCOUNT_LAST_NAME"
    private val ACCOUNT_FIRST_NAME = "ACCOUNT_FIRST_NAME"
    private val ACCOUNT_SECOND_NAME = "ACCOUNT_SECOND_NAME"

    private val compositeDisposable: CompositeDisposable

    val card: Card
    val account: Account

    val accountTypeFieldProperties: FieldProperties
    val cardFieldProperties: FieldProperties
    val mfoFieldProperties: FieldProperties
    val accountFieldProperties: FieldProperties
    val paymentTypeFieldProperties: FieldProperties
    val lastNameFieldProperties: FieldProperties
    val firstNameFieldProperties: FieldProperties
    val secondNameFieldProperties: FieldProperties

    init
    {
        compositeDisposable = CompositeDisposable()

        card = Card()
        account = Account()

        val accountTypeViewProperties = ViewProperties(view.getContext().getString(R.string.account_type_title), view.getContext().getString(R.string.account_type_prompt))
        val accountTypeValidator = RegexValidator(RegexPredicate("^2$|^5$"), view.getContext().getString(R.string.account_validator_message))
        accountTypeFieldProperties = FieldProperties(accountTypeValidator, accountTypeViewProperties)

        val cardNumberViewProperties = ViewProperties(view.getContext().getString(R.string.card_number_title), view.getContext().getString(R.string.card_number_prompt))
        val cardValidator = RegexValidator(RegexPredicate("^\\d{4} \\d{4} \\d{4} \\d{4,7}$"), view.getContext().getString(R.string.card_number_validator_message))
        cardFieldProperties = FieldProperties(cardValidator, cardNumberViewProperties)

        val mfoViewProperties = ViewProperties(view.getContext().getString(R.string.mfo_title), view.getContext().getString(R.string.mfo_prompt))
        val mfoValidator = RegexValidator(RegexPredicate("^\\d{9}$"), view.getContext().getString(R.string.mfo_validator_message))
        mfoFieldProperties = FieldProperties(mfoValidator, mfoViewProperties)

        val accountNumberViewProperties = ViewProperties(view.getContext().getString(R.string.account_number_title), view.getContext().getString(R.string.account_number_prompt))
        val accountValidator = RegexValidator(RegexPredicate("^\\d{20}$"), view.getContext().getString(R.string.account_number_validator_message))
        accountFieldProperties = FieldProperties(accountValidator, accountNumberViewProperties)

        val paymentTypeViewProperties = ViewProperties(view.getContext().getString(R.string.payment_type_title), view.getContext().getString(R.string.payment_type_prompt))
        val paymentTypeValidator = RegexValidator(RegexPredicate("^0$|^1$"), view.getContext().getString(R.string.payment_type_validator_message))
        paymentTypeFieldProperties = FieldProperties(paymentTypeValidator, paymentTypeViewProperties)

        val nameValidator = RegexValidator(RegexPredicate("^[а-яА-Я\\-\\s]{2,40}$"), view.getContext().getString(R.string.name_validator_message))

        val lastNameViewProperties = ViewProperties(view.getContext().getString(R.string.last_name_title), view.getContext().getString(R.string.last_name_prompt))
        lastNameFieldProperties = FieldProperties(nameValidator, lastNameViewProperties)

        val firstNameViewProperties = ViewProperties(view.getContext().getString(R.string.first_name_title), view.getContext().getString(R.string.first_name_prompt))
        firstNameFieldProperties = FieldProperties(nameValidator, firstNameViewProperties)

        val secondNameViewProperties = ViewProperties(view.getContext().getString(R.string.second_name_title), view.getContext().getString(R.string.second_name_prompt))
        secondNameFieldProperties = FieldProperties(nameValidator, secondNameViewProperties)
    }

    var selectedAccountType: AccountType = AccountType.ACCOUNT
        set(value)
        {
            field = value

            notifyPropertyChanged(BR.cardDataFieldVisibility)
            notifyPropertyChanged(BR.accountDataFieldVisibility)
        }

    fun getAccountTypeAdapter() = EnumArrayAdapter(view.getContext(), AccountType.values(), { it.title })

    fun getPaymentTypeAdapter() = EnumArrayAdapter(view.getContext(), PaymentType.values(), { it.title })

    fun cardInputTextWatcher() = FourDigitCardFormatWatcher()

    @Bindable
    fun getCardDataFieldVisibility() = checkRegexMatch("^5$")

    @Bindable
    fun getAccountDataFieldVisibility() = checkRegexMatch("^2$")

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)

        outState.putString(SELECTED_ACCOUNT_TYPE, selectedAccountType.toString())

        outState.putString(CARD_NUMBER, card.number)

        outState.putString(ACCOUNT_MFO, account.mfo)
        outState.putString(ACCOUNT_NUMBER, account.number)
        if (account.paymentType != null)
        {
            outState.putString(ACCOUNT_PAYMENT_TYPE, account.paymentType!!.toString())
        }
        outState.putString(ACCOUNT_LAST_NAME, account.lastName)
        outState.putString(ACCOUNT_FIRST_NAME, account.firstName)
        outState.putString(ACCOUNT_SECOND_NAME, account.secondName)
    }

    override fun onResume()
    {
        super.onResume()
        addObservables()
    }

    override fun onPause()
    {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?)
    {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null)
        {
            selectedAccountType = AccountType.valueOf(savedInstanceState.getString(SELECTED_ACCOUNT_TYPE))

            card.number = savedInstanceState.getString(CARD_NUMBER)

            account.mfo = savedInstanceState.getString(ACCOUNT_MFO)
            account.number = savedInstanceState.getString(ACCOUNT_NUMBER)
            val paymentTypeString = savedInstanceState.getString(ACCOUNT_PAYMENT_TYPE)
            if (paymentTypeString != null)
            {
                account.paymentType = PaymentType.valueOf(paymentTypeString)
            }
            account.lastName = savedInstanceState.getString(ACCOUNT_LAST_NAME)
            account.firstName = savedInstanceState.getString(ACCOUNT_FIRST_NAME)
            account.secondName = savedInstanceState.getString(ACCOUNT_SECOND_NAME)
        }
    }

    private fun checkRegexMatch(regex: String): Int
    {
        if (Pattern.matches(regex, selectedAccountType.value.toString()))
        {
            return View.VISIBLE
        }
        else
        {
            return View.GONE
        }
    }

    private fun addObservables()
    {
        val accountTypeSpinnerObservable = setSpinnerObservable(
                view.getAccountType(),
                {
                    selectedAccountType = view.getAccountType().selectedItem as AccountType
                }
        )
        val paymentTypeSpinnerObservable = setSpinnerObservable(
                view.getPaymentType(),
                {
                    account.paymentType = view.getPaymentType().selectedItem as PaymentType
                }
        )
        val cardNumberObservable = setEditTextObservable(
                view.getCardNumberTextView(),
                view.getCardNumberTextInputLayout(),
                cardFieldProperties.validator,
                { card.number = it.replace(" ", "") }
        )
        val mfoObservable = setEditTextObservable(
                view.getMfoTextView(),
                view.getMfoTextInputLayout(),
                mfoFieldProperties.validator,
                { account.mfo = it }
        )
        val accountNumberObservable = setEditTextObservable(
                view.getAccountNumberTextView(),
                view.getAccountNumberTextInputLayout(),
                accountFieldProperties.validator,
                { account.number = it }
        )
        val lastNameObservable = setEditTextObservable(
                view.getLastNameTextView(),
                view.getLastNameTextInputLayout(),
                lastNameFieldProperties.validator,
                { account.lastName = it }
        )
        val firstNameObservable = setEditTextObservable(
                view.getLastNameTextView(),
                view.getLastNameTextInputLayout(),
                firstNameFieldProperties.validator,
                { account.firstName = it }
        )
        val secondNameObservable = setEditTextObservable(
                view.getLastNameTextView(),
                view.getLastNameTextInputLayout(),
                secondNameFieldProperties.validator,
                { account.secondName = it }
        )

        compositeDisposable.addAll(
                accountTypeSpinnerObservable,
                paymentTypeSpinnerObservable,
                cardNumberObservable,
                mfoObservable,
                accountNumberObservable,
                lastNameObservable,
                firstNameObservable,
                secondNameObservable
        )
    }

    private fun setSpinnerObservable(view: Spinner, onNext: (Int) -> Unit): Disposable
    {
        return RxAdapterView.itemSelections<SpinnerAdapter>(view)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext)
    }

    private fun setEditTextObservable(
            view: TextView,
            layout: TextInputLayout,
            validator: Validator,
            callback: (String) -> Unit
    ): Disposable
    {
        return RxTextView.textChanges(view)
                .skip(1)
                .debounce(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { charSequence ->
                    val input = charSequence.toString()
                    if (!validator.validate(input))
                    {
                        layout.error = validator.message
                    }
                    else
                    {
                        layout.error = null
                        callback(input)
                    }
                }
    }
}