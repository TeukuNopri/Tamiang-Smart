package com.tamiang.smart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tamiang.smart.R
import com.tamiang.smart.models.InfoKamar
import kotlinx.android.synthetic.main.item_info_kamar.view.*

class InfoKamarAdapter(val data: List<InfoKamar>?):RecyclerView.Adapter<InfoKamarAdapter.InfoKamarHolder>() {

    class InfoKamarHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nm_kamar    = itemView.tv_kamar_nm_bangsal
        val jumlah      = itemView.tv_kamar_jumlah
        val isi         = itemView.tv_kamar_isi
        val kosong      = itemView.tv_kamar_kosong
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoKamarHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_info_kamar, parent, false)
        val holder = InfoKamarHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder: InfoKamarHolder, position: Int) {
        holder.nm_kamar.text = data?.get(position)?.nm_bangsal
        holder.jumlah.text = data?.get(position)?.jumlah
        holder.isi.text = data?.get(position)?.isi
        holder.kosong.text = data?.get(position)?.kosong
    }

    override fun getItemCount(): Int {
        return data?.size?: 0
    }
}