package com.tamiang.smart.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.RadioButton
import com.tamiang.smart.R
import com.tamiang.smart.adapter.PoliklinikAdapterSpinner
import com.tamiang.smart.adapter.ReferensiDokterSpinner
import com.tamiang.smart.adapter.RujukanAdapterSpinner
import com.tamiang.smart.models.*
import com.tamiang.smart.network.NetworkConfig
import com.tamiang.smart.sharepreflogin.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_daftar_poli.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DaftarPoliFragment : Fragment()  {

    var cal = Calendar.getInstance()
    var formate = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    lateinit var radioButton: RadioButton

    lateinit var poliklinikAdapterSpinner : PoliklinikAdapterSpinner
    lateinit var referensiDokterSpinner: ReferensiDokterSpinner
    lateinit var rujukanAdapterSpinner: RujukanAdapterSpinner

    private var item : ArrayList<ReferensiPoli> = ArrayList()
    private var itemDokter: ArrayList<ReferensiDokter> = ArrayList()

    //VARIABLE INPUT
    var kode_bayar : String? = null
    var kode_rujukan: String? = null
    var kode_poli: String? = null
    var kode_dokter: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daftar_poli, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedDate = Calendar.getInstance()

        val date = formate.format(selectedDate.time)
        et_pilih_tgl.setText(date.toString())

//        rv_daftar_poli.layoutManager = GridLayoutManager(view.context, 2)

        showReferensiPoli(date.toString())


//       Spinner poli
        sp_poliklinik.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedItemPoli = poliklinikAdapterSpinner.getItem(position)
                val tgl_rencana_berobat = et_pilih_tgl.text.toString().trim()
                kode_poli = selectedItemPoli.kd_poli
                showReferensiDokter(tgl_rencana_berobat, selectedItemPoli.kd_poli.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        // Spinner Dokter
        sp_dokter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectItemDokter = referensiDokterSpinner.getItem(position)
                kode_dokter = selectItemDokter.kd_dokter
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        // option
        val selectedOption: Int = rg_jenis_bayar!!.checkedRadioButtonId
        radioButton = view.findViewById(selectedOption)
        if(radioButton.text.toString().trim() == "Umum"){
            kode_bayar = "A09"
        }

        rg_jenis_bayar.setOnCheckedChangeListener{ group, checkedId ->
            val radio: RadioButton? = view.findViewById(checkedId)

            if(radio?.text.toString().trim() == "Umum"){
                kode_bayar = "A09"
                ly_rujukan.visibility = View.GONE
            }else{
                kode_bayar = "A65"
                ly_rujukan.visibility = View.VISIBLE

                // spinner rujukan
                val rujukan = arrayListOf<RujukanBpjs>()
                rujukan.add(RujukanBpjs("1", "Faskes 1"))
                rujukan.add(RujukanBpjs("2", "Faskes 2"))

                rujukanAdapterSpinner = RujukanAdapterSpinner(view.context, rujukan)
                sp_rujukan.adapter = rujukanAdapterSpinner

                sp_rujukan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                        val selectedItemRujukan = rujukanAdapterSpinner.getItem(position)
                        kode_rujukan = selectedItemRujukan.kd_rujukan
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                }
            }
        }


        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" //
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                et_pilih_tgl.setText(sdf.format(cal.getTime()))
            }
        }

        et_pilih_tgl.setOnClickListener {
            DatePickerDialog(view.context, dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        et_pilih_tgl.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                showReferensiPoli(et_pilih_tgl.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        btn_lanjut_poliklinik.setOnClickListener {
            if(kode_bayar == "A09"){
                kode_rujukan = null
            }

            val no_rekam_medis      = SharedPrefManager.getInstance(view?.context!!).login.no_rkm_medis
            val tgl_lahir           = SharedPrefManager.getInstance(view?.context!!).login.tgl_lahir
            val tgl_rencana_berobat = et_pilih_tgl.text.toString()
            val rujukan             = kode_rujukan.toString().trim()
            val kd_poli             = kode_poli.toString().trim()
            val kd_dokter           = kode_dokter.toString().trim()
            val kd_jenis_bayar      = kode_bayar.toString().trim()

            Log.d("no_rekam_medis", no_rekam_medis.toString())
            Log.d("tgl_lahir", tgl_lahir.toString())
            Log.d("tgl_rencana_berobat", tgl_rencana_berobat)
            Log.d("kode_poli", kd_poli)
            Log.d("rujukan", rujukan)
            Log.d("dokter", kd_dokter)
            Log.d("referensi_bayar", kd_jenis_bayar)

            val builder = AlertDialog.Builder(view?.context)
            builder.setMessage("Apakah Anda Yakin Ingin Mendaftar Ke Poliklinik Rawat Jalan...?")
                .setCancelable(false)
                .setPositiveButton("Ya"){
                        dialog, id -> savePoliklinik(no_rekam_medis.toString(), tgl_lahir.toString(), tgl_rencana_berobat, kd_poli, kd_jenis_bayar, kd_dokter, rujukan)
                }
                .setNegativeButton("Tidak"){
                        dialog, id-> dialog.dismiss()
                }

            val alert = builder.create()
            alert.show()

        }
    }

    private fun showReferensiPoli(tgl: String?){
        pb_daftar_poli.visibility = View.VISIBLE
        NetworkConfig.getInstance().getReferensiPoli(tgl)
            .enqueue(object: Callback<ReferensiPoliResponse>{
                override fun onResponse(
                    call: Call<ReferensiPoliResponse>,
                    response: Response<ReferensiPoliResponse>
                ) {
                    pb_daftar_poli.visibility = View.GONE

                    item = response.body()?.response as ArrayList<ReferensiPoli>
                    poliklinikAdapterSpinner = PoliklinikAdapterSpinner(view?.context!!, item)

                    sp_poliklinik.adapter = poliklinikAdapterSpinner
                }

                override fun onFailure(call: Call<ReferensiPoliResponse>, t: Throwable) {
                    Log.e("Network Error" , t.message.toString())
                }
            })
    }

    private fun showReferensiDokter(tgl: String?, kd_poli: String?){
        pb_daftar_poli.visibility = View.VISIBLE
        NetworkConfig.getInstance().getReferensiDokter(tgl, kd_poli)
            .enqueue(object : Callback<ReferensiDokterResponse>{
                override fun onResponse(
                    call: Call<ReferensiDokterResponse>,
                    response: Response<ReferensiDokterResponse>
                ) {
                    pb_daftar_poli.visibility = View.GONE

                    itemDokter = response.body()?.response as ArrayList<ReferensiDokter>
                    referensiDokterSpinner = ReferensiDokterSpinner(view?.context!!, itemDokter)
                    sp_dokter.adapter = referensiDokterSpinner
                }

                override fun onFailure(call: Call<ReferensiDokterResponse>, t: Throwable) {
                   Log.d("Network Error", t.message.toString())
                }

            })
    }


    private fun savePoliklinik(no_rekam_medis: String?, tgl_lahir: String?, tgl_rencana_berobat: String?, kd_poli: String? ,referensi_jns_bayar: String?, kd_dokter: String?, asalrujukan: String?){
        pb_daftar_poli.visibility = View.VISIBLE
        NetworkConfig.getInstance().saveDaftarBerobat(no_rekam_medis, tgl_lahir, tgl_rencana_berobat, kd_poli, referensi_jns_bayar, kd_dokter, asalrujukan)
            .enqueue(object : Callback<DaftarBerobatResponse>{
                override fun onResponse(
                    call: Call<DaftarBerobatResponse>,
                    response: Response<DaftarBerobatResponse>
                ) {
                    pb_daftar_poli.visibility = View.GONE

                    if(response.body()?.metaData?.kode == 202){
                        val builder = AlertDialog.Builder(view?.context)
                        builder.setMessage(response.body()?.metaData?.message.toString())
                            .setCancelable(false)
                            .setPositiveButton("Ya"){  dialog, id ->
                                dialog.dismiss()
                            }

                        val alert = builder.create()
                        alert.show()
                    }else{

                        val builder = AlertDialog.Builder(view?.context)
                        builder.setTitle("Antrian : " + response.body()?.response?.no_antri_poli.toString())
                        builder.setMessage("Kode Boking : " + response.body()?.response?.kode_boking.toString() + "\n" +"\n"+response.body()?.response?.keterangan.toString())
                            .setCancelable(false)
                            .setPositiveButton("Ya"){  dialog, id ->
                                val fragment = BerandaFragment()
                                fragmentManager?.beginTransaction()?.replace(R.id.view_home, fragment)?.commit()
                            }

                        val alert = builder.create()
                        alert.show()
                    }
                }

                override fun onFailure(call: Call<DaftarBerobatResponse>, t: Throwable) {
                    Log.e("Network Error", t.message.toString())
                }

            })
    }
}