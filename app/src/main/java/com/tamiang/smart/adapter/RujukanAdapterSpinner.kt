package com.tamiang.smart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.tamiang.smart.R
import com.tamiang.smart.models.RujukanBpjs

class RujukanAdapterSpinner(val context: Context, val data: List<RujukanBpjs>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): RujukanBpjs {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: itemHolder

        if(convertView == null){
            view = inflater.inflate(R.layout.custome_spinner_rujukan, parent, false)
            vh = itemHolder(view)
            view?.tag = vh
        }else{
            view = convertView
            vh = view.tag as itemHolder
        }

        vh.nm_rujukan.text = data.get(position).nm_rujukan

        return view
    }

    private class itemHolder(row: View){
        val nm_rujukan: TextView

        init {
            nm_rujukan = row?.findViewById(R.id.tv_rujukan) as TextView
        }
    }
}