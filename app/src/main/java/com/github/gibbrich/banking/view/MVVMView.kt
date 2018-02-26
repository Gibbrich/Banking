package com.github.gibbrich.banking.view

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.widget.Spinner
import android.widget.TextView

/**
 * Created by Dvurechenskiyi on 26.02.2018.
 */
interface MVVMView
{
}

interface MainActivityView: MVVMView
{
    fun getContext(): Context

    fun getAccountType(): Spinner
    fun getPaymentType(): Spinner

    fun getCardNumberTextInputLayout(): TextInputLayout
    fun getCardNumberTextView(): TextView

    fun getMfoTextInputLayout(): TextInputLayout
    fun getMfoTextView(): TextView
    
    fun getAccountNumberTextInputLayout(): TextInputLayout
    fun getAccountNumberTextView(): TextView
    
    fun getLastNameTextInputLayout(): TextInputLayout
    fun getLastNameTextView(): TextView

    fun getFirstNameTextInputLayout(): TextInputLayout
    fun getFirstNameTextView(): TextView
    
    fun getSecondNameTextInputLayout(): TextInputLayout
    fun getSecondNameTextView(): TextView
}