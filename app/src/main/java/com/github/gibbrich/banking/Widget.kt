package com.github.gibbrich.banking

import android.content.Context
import android.support.annotation.StringRes
import android.widget.ArrayAdapter

/**
 * Created by Dvurechenskiyi on 21.02.2018.
 */
abstract class Widget(
        val context: Context,
        @StringRes val title: Int,
        @StringRes val prompt: Int
)

class RadioWidget<T>(
        context: Context,
        title: Int,
        prompt: Int,
        choices: Array<T>
) : Widget(context, title, prompt)
{
    val adapter: ArrayAdapter<T> = ArrayAdapter(context, android.R.layout.simple_spinner_item, choices)
}

// TODO: 21.02.2018 change inputType type from string to attribute value
class TextWidget(
        context: Context,
        title: Int,
        prompt: Int,
        inputType: String = ""
) : Widget(context, title, prompt)
{

}

