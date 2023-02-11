package com.tamiang.smart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(imageUrl: ArrayList<String>) : SliderViewAdapter<SliderAdapter.SlideViewHolder>(){

    var slideList: ArrayList<String> = imageUrl

    override fun getCount(): Int {
        return slideList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SlideViewHolder {
        val inflate: View = LayoutInflater.from(parent!!.context).inflate(R.layout.slider_item, null)

        return SlideViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: SlideViewHolder?, position: Int) {
        if(viewHolder != null){
            Glide.with(viewHolder.itemView).load(slideList.get(position)).fitCenter().into(viewHolder.imageView)
        }
    }

    class SlideViewHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView){
        var imageView: ImageView = itemView!!.findViewById(R.id.myimage)
    }
}