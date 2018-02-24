package com.github.gibbrich.banking.model

/**
 * Created by Артур on 24.02.2018.
 */
open class ViewProperties(
        val title: String,
        val prompt: String
)

class TextViewProperties(
        title: String,
        prompt: String,
        val keyboard: String? = null
): ViewProperties(title, prompt)

class SpinnerViewProperties<T>(
        title: String,
        prompt: String,
        val array: Array<T>
): ViewProperties(title, prompt)