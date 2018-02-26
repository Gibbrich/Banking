package com.github.gibbrich.banking.viewModel

import android.databinding.BaseObservable
import android.os.Bundle



/**
 * Created by Dvurechenskiyi on 26.02.2018.
 */
abstract class BaseViewModel: BaseObservable()
{
    open fun onResume()
    {
    }

    open fun onPause()
    {
    }

    open fun onRestoreInstanceState(savedInstanceState: Bundle?)
    {
    }

    open fun onSaveInstanceState(outState: Bundle)
    {
    }
}