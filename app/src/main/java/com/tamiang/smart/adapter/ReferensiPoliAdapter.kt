package com.tamiang.smart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tamiang.smart.R
import com.tamiang.smart.models.ReferensiPoli
import kotlinx.android.synthetic.main.item_daftar_poli.view.*

class ReferensiPoliAdapter (val data: List<ReferensiPoli>?, val itemClick: onClickListener): RecyclerView.Adapter<ReferensiPoliAdapter.ReferensiPoliHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferensiPoliHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daftar_poli, parent, false)
        val holder =ReferensiPoliAdapter.ReferensiPoliHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder: ReferensiPoliHolder, position: Int) {
        val item = data?.get(position)

        holder.nama_poli.text = data?.get(position)?.nm_poli
        holder.jam_mulai.text = data?.get(position)?.jam_mulai
        holder.jam_selesai.text = data?.get(position)?.jam_selesai
        holder.kuota.text = data?.get(position)?.kuota

        holder.itemView.setOnClickListener {
            itemClick.detail(item)
        }
    }

    override fun getItemCount(): Int {
        return data?.size?:0
    }

    class ReferensiPoliHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nama_poli   = itemView.tv_nm_poli_show
        val jam_mulai   = itemView.tv_jam_mulai_show
        val jam_selesai = itemView.tv_jam_selesai_show
        val kuota       = itemView.tv_kuota_show
    }

    interface onClickListener{
        fun detail(item: ReferensiPoli?)
    }


}