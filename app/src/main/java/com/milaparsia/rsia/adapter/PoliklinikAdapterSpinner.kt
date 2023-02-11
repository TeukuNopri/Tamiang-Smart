package com.milaparsia.rsia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.getSystemService
import com.milaparsia.rsia.R
import com.milaparsia.rsia.models.ReferensiPoli
import org.w3c.dom.Text

class PoliklinikAdapterSpinner (val context: Context, val data: List<ReferensiPoli>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): ReferensiPoli{
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if(convertView == null){
            view = inflater.inflate(R.layout.custom_spinner_poliklinik, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        }else{
            view = convertView
            vh = view.tag as ItemHolder
        }

        vh.nm_poli.text = data.get(position).nm_poli

        return view
    }

    private class ItemHolder(row: View?){
        val nm_poli: TextView

        init {
            nm_poli = row?.findViewById(R.id.tv_nm_poli_show) as TextView
        }
    }
}