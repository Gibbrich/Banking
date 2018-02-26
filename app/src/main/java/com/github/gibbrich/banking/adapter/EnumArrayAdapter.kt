package com.github.gibbrich.banking.adapter

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by Dvurechenskiyi on 26.02.2018.
 */
class EnumArrayAdapter<out T>(private val context: Context, private val items: Array<T>, private val getString: (T) -> String) : BaseAdapter()
{
    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = items.size

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View
    {
        val holder: ViewHolder
        var view: View? = view

        if (view?.tag == null)
        {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)

            holder = ViewHolder(view.findViewById(R.id.text1))
            view.tag = holder
        }
        else
        {
            holder = view.tag as ViewHolder
        }

        val item = getItem(position)
        holder.textView.text = getString(item)

        return view!!
    }
}

private class ViewHolder(val textView: TextView)