package com.github.gibbrich.banking.model

import android.text.Editable
import android.text.TextWatcher
import android.text.TextUtils
import java.nio.file.Files.delete



/**
 * Created by Dvurechenskiyi on 22.02.2018.
 */
class FourDigitCardFormatWatcher : TextWatcher
{
    private val space = ' '

    override fun afterTextChanged(s: Editable)
    {
        // Remove spacing char
        if (s.isNotEmpty() && s.length % 5 == 0)
        {
            val c = s[s.length - 1]
            if (space == c)
            {
                s.delete(s.length - 1, s.length)
            }
        }
        // Insert char where needed.
        if (s.isNotEmpty() && s.length % 5 == 0)
        {
            val c = s[s.length - 1]
            // Only if its a digit where there should be a space we insert a space
            if (Character.isDigit(c) && TextUtils.split(s.toString(), space.toString()).size <= 3)
            {
                s.insert(s.length - 1, space.toString())
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
    {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
    {
    }

}