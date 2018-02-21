package com.github.gibbrich.banking.model

import com.github.gibbrich.banking.RadioWidget
import com.github.gibbrich.banking.TextWidget
import com.github.gibbrich.banking.Widget

/**
 * Created by Dvurechenskiyi on 21.02.2018.
 */
abstract class Field(
        open val widget: Widget,
        open val validator: Predicate
)

class SpinnerField<T>(
        override val widget: RadioWidget<T>,
        override val validator: RegexPredicate
): Field(widget, validator)

class TextField(
        override val widget: TextWidget,
        override val validator: RegexPredicate
) : Field(widget, validator)