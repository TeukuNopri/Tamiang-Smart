package com.tamiang.smart.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.tamiang.smart.R
import com.tamiang.smart.adapter.InfoKamarAdapter
import com.tamiang.smart.models.InfoKamarResponse
import com.tamiang.smart.network.NetworkConfig
import kotlinx.android.synthetic.main.fragment_info_kamar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoKamarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_kamar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_info_kamar.addItemDecoration(DividerItemDecoration(view.context, 0))

        getInfoKamar()

        sr_info_kamar.setOnRefreshListener {
            sr_info_kamar.isRefreshing = true
            getInfoKamar()
            sr_info_kamar.isRefreshing = false
        }
    }

    private fun getInfoKamar(){
        pb_info_kamar.visibility =View.VISIBLE
        NetworkConfig.getInstance().getInfoKamar()
            .enqueue(object : Callback<InfoKamarResponse>{
                override fun onResponse(
                    call: Call<InfoKamarResponse>,
                    response: Response<InfoKamarResponse>
                ) {
                    pb_info_kamar.visibility =View.GONE

                    val item = response.body()?.response

                    val adapter = InfoKamarAdapter(item)

                    rv_info_kamar.adapter = adapter
                }

                override fun onFailure(call: Call<InfoKamarResponse>, t: Throwable) {
                    Log.e("Network Error", t.message.toString())
                }

            })
    }
}