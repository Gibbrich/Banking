package com.github.gibbrich.banking.model

import android.content.Context
import android.databinding.ObservableBoolean
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

/**
 * Created by Артур on 24.02.2018.
 */
abstract class FieldViewModel(
        open val validator: Validator,
        open val viewProperties: ViewProperties,
        open val view: View,
        val condition: RegexCondition? = null
)
{
    val isVisible: ObservableBoolean = ObservableBoolean(true)
}

class TextFieldViewModel(
        override val validator: RegexValidator,
        override val viewProperties: TextViewProperties,
        override val view: TextView,
        condition: RegexCondition? = null
) : FieldViewModel(validator, viewProperties, view, condition)

class SpinnerFieldViewModel<T>(
        override val validator: RegexValidator,
        override val viewProperties: SpinnerViewProperties<T>,
        override val view: Spinner,
        context: Context,
        condition: RegexCondition? = null
) : FieldViewModel(validator, viewProperties, view, condition)
{
    init
    {
        view.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, viewProperties.array)
    }
}