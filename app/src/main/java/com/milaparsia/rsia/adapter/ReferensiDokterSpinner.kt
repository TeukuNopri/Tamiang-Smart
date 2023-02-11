package com.milaparsia.rsia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.getSystemService
import com.milaparsia.rsia.R
import com.milaparsia.rsia.models.ReferensiDokter

class ReferensiDokterSpinner (val context: Context, val data: List<ReferensiDokter>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): ReferensiDokter {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: itemHolder

        if(convertView == null){
            view = inflater.inflate(R.layout.custom_spinner_dokter, parent, false)
            vh = itemHolder(view)
            view?.tag = vh
        }else{
            view = convertView
            vh = view.tag as itemHolder
        }

        vh.nm_dokter.text = data?.get(position)?.nm_dokter

        return view
    }

    private class itemHolder(row: View?){
        val nm_dokter: TextView

        init {
            nm_dokter = row?.findViewById(R.id.tv_nm_dokter) as TextView
        }
    }
}