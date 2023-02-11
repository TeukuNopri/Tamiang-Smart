package com.tamiang.smart.fragments

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.tamiang.smart.MapsAmbulanceActivity
import com.tamiang.smart.R
import com.tamiang.smart.SliderAdapter
import com.tamiang.smart.models.AntrianPoliResponse
import com.tamiang.smart.models.AntrianResepResponse
import com.tamiang.smart.network.NetworkConfig
import com.tamiang.smart.sharepreflogin.SharedPrefManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.bottom_sheet_media_sosial.view.*
import kotlinx.android.synthetic.main.bottom_sheet_telepon.view.*
import kotlinx.android.synthetic.main.fragment_beranda.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BerandaFragment : Fragment() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lat: String? = null
    private var long: String? = null

    lateinit var imageUrl: ArrayList<String>
    lateinit var sliderAdapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreference = view.context.getSharedPreferences("TOKEN_FCM", Context.MODE_PRIVATE)
        val token = sharedPreference?.getString("token", "").toString()

        Log.d("token", token)


        val no_rek_medis = SharedPrefManager.getInstance(view?.context!!).login.no_rkm_medis
        tv_up_nm_pasien.setText(SharedPrefManager.getInstance(view?.context!!).login.nm_pasien)

        tv_no_rek_medis.setText(SharedPrefManager.getInstance(view?.context!!).login.no_rkm_medis)
        tv_tm_lahir_pasien.setText(SharedPrefManager.getInstance(view?.context!!).login.tmp_lahir)
        tv_tgl_lahir_pasien.setText(SharedPrefManager.getInstance(view?.context!!).login.tgl_lahir)
        tv_alamat_pasien.setText(SharedPrefManager.getInstance(view?.context!!).login.alamatpj)


        // Slide Image
        imageUrl = ArrayList()
        imageUrl = (imageUrl + "https://regonrsia.acehprov.go.id/assets/1.jpg") as ArrayList<String>
        imageUrl = (imageUrl + "https://regonrsia.acehprov.go.id/assets/2.jpg") as ArrayList<String>
        imageUrl = (imageUrl + "https://regonrsia.acehprov.go.id/assets/3.jpg") as ArrayList<String>

        sliderAdapter = SliderAdapter(imageUrl)
        slider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        slider.setSliderAdapter(sliderAdapter)
        slider.scrollTimeInSec = 3
        slider.isAutoCycle = true
        slider.startAutoCycle()


        // Action Button
        btn_no_rm.setOnClickListener{
//            Log.d("no rekam medis", no_rek_medis.toString())
//            val dialog = BottomSheetDialog(view.context)
//            val view: View = layoutInflater.inflate(R.layout.bottom_sheet_qr, null)
//
//            // create QR
//            val image = dialog.findViewById<ImageView>(R.id.iv_qr_code_bottom)
//            val writer = QRCodeWriter()
//            try{
//                val bitMatrix = writer.encode(no_rek_medis, BarcodeFormat.QR_CODE, 1080 , 1080)
//                val width = bitMatrix.width
//                val height = bitMatrix.height
//
//                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
//                for (x in 0 until width){
//                    for (y in 0 until height){
//                        bmp.setPixel(x, y, if(bitMatrix[x, y]) Color.BLACK else Color.WHITE)
//                    }
//                }
//
//                image?.setImageBitmap(bmp)
//            }catch (e: WriterException){
//
//            }
//            dialog.setContentView(view)
//            dialog.show()


//            val bottomSheetQrFragment = BottomSheetQrFragment()
//            bottomSheetQrFragment.show(requireActivity().supportFragmentManager, "BottomSheetQr")

//            find

            val dialog = layoutInflater.inflate(R.layout.custom_dialog_box, null)
            val image = dialog.findViewById<ImageView>(R.id.iv_qr_code_dialog)
            val text  = dialog.findViewById<TextView>(R.id.dialog_no_rekam_medis)

            text.text = no_rek_medis

            val writer = QRCodeWriter()

            try{
                val bitMatrix = writer.encode(no_rek_medis, BarcodeFormat.QR_CODE, 1080 , 1080)
                val width = bitMatrix.width
                val height = bitMatrix.height

                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                for (x in 0 until width){
                    for (y in 0 until height){
                        bmp.setPixel(x, y, if(bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }

                image.setImageBitmap(bmp)
            }catch (e: WriterException){

            }

            val myDialog = Dialog(view.context)
            myDialog.setContentView(dialog)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
        }

        btn_telepon.setOnClickListener {
            val dialog = BottomSheetDialog(view.context)
            val view: View = layoutInflater.inflate(R.layout.bottom_sheet_telepon, null)

            val wa_info = view.btn_info_wa
            val telepon_info = view.btn_info_telepon

            wa_info?.setOnClickListener {
                val number = "6282286455084"
                val url = "https://api.whatsapp.com/send?phone="+number

                val i = Intent(Intent.ACTION_VIEW)
                i.setPackage("com.whatsapp")
                i.setData(Uri.parse(url))
                startActivity(Intent(i))
            }

            telepon_info.setOnClickListener {
                val number = "082286455084"
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + number)
                startActivity(dialIntent)
            }

            dialog.setContentView(view)
            dialog.show()

        }

        btn_lokasi_maps.setOnClickListener {
            val url = "https://goo.gl/maps/YnZvgMMUamzeepNJ9"
            val i = Intent(Intent.ACTION_VIEW)
            i.setPackage("com.google.android.apps.maps")
            i.setData(Uri.parse(url))
            startActivity(Intent(i))
        }

        btn_media_sosial.setOnClickListener {
            val dialog = BottomSheetDialog(view.context)
            val view: View = layoutInflater.inflate(R.layout.bottom_sheet_media_sosial, null)
            val instagram_info = view.btn_info_instagram
            val facebook_info = view.btn_info_fb

            instagram_info.setOnClickListener {
                val url = "https://www.instagram.com/rsiaaceh/"
                val i = Intent(Intent.ACTION_VIEW)
                i.setPackage("com.instagram.android")
                i.setData(Uri.parse(url))
                startActivity(Intent(i))
            }

            facebook_info.setOnClickListener {
                val url = "https://www.facebook.com/rsia.aceh.9"
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse(url))
                startActivity(Intent(i))
            }

            dialog.setContentView(view)
            dialog.show()
        }

        btn_ambulance.setOnClickListener {
            startActivity(Intent(view.context, MapsAmbulanceActivity::class.java))
        }

//        createQRCode(no_rek_medis)

        actionBeranda(no_rek_medis)

        btn_refresh_antrian.setOnClickListener {
            actionBeranda(no_rek_medis)
        }


//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view?.context)
//        getAntrianPoli(no_rek_medis)
    }

    private fun actionBeranda(no_rek_medis: String?){
        pb_antrian.visibility = View.VISIBLE
        NetworkConfig.getInstance().getCekAntrianPoli(no_rek_medis)
            .enqueue(object: Callback<AntrianPoliResponse> {
                override fun onResponse(
                    call: Call<AntrianPoliResponse>,
                    response: Response<AntrianPoliResponse>
                ) {
                    if(response.body()?.metaData?.kode == 202){
                        ly_slide_image.visibility = View.VISIBLE
                        pb_antrian.visibility = View.GONE
                        ly_antrian_poli.visibility = View.GONE
                    }else{
                        pb_antrian.visibility = View.GONE
                        ly_slide_image.visibility = View.GONE
                        ly_antrian_poli.visibility = View.VISIBLE

                        getAntrianResep(no_rek_medis)

                        tv_nomor_antrian_poli.text = response.body()?.response?.nomorantrean
                        tv_nama_poli.text = response.body()?.response?.namapoli
                        tv_dokter_poli.text = response.body()?.response?.namadokter
                        tv_sisa_antrian_poli.text = response.body()?.response?.sisaantrean
                        tv_antrian_panggil_poli.text = response.body()?.response?.antreanpanggil
                        tv_status_poli.text = response.body()?.response?.statusantrian
                        tv_keterangan_poli.text = response.body()?.response?.keterangan
                    }

                    Log.d("TAG", response.body()?.response.toString())
                }

                override fun onFailure(call: Call<AntrianPoliResponse>, t: Throwable) {
                    Log.e("Network Error : ", t.message.toString())
                }

            })
    }

    private fun getAntrianResep(no_rek_medis: String?){
        NetworkConfig.getInstance().getAntrianResep(no_rek_medis)
            .enqueue(object: Callback<AntrianResepResponse>{
                override fun onResponse(
                    call: Call<AntrianResepResponse>,
                    response: Response<AntrianResepResponse>
                ) {
                    if(response.body()?.metaData?.kode == 202){

                        ly_antrian_resep.visibility = View.GONE
                    }else{
                        ly_antrian_resep.visibility = View.VISIBLE
                        tv_nomor_resep.text = response.body()?.response?.no_resep
                        tv_status_resep.text = response.body()?.response?.status_resep
                    }

                    Log.d("TAG", response.body()?.response.toString())
                }

                override fun onFailure(call: Call<AntrianResepResponse>, t: Throwable) {
                    Log.e("Network Error : ", t.message.toString())
                }

            })
    }

    private fun getLocation(){
        if (ActivityCompat.checkSelfPermission(
                view?.context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                view?.context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            activity?.let { it1 -> ActivityCompat.requestPermissions(it1, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101) }

        }

        val task = fusedLocationProviderClient.lastLocation

        task.addOnSuccessListener {
            lat = it.latitude.toString()
            long = it.longitude.toString()
        }
    }

//    private fun createQRCode(no_rek_medis: String?){
//
//        val writer = QRCodeWriter()
//
//        try{
//            val bitMatrix = writer.encode(no_rek_medis, BarcodeFormat.QR_CODE, 350 , 350)
//            val width = bitMatrix.width
//            val height = bitMatrix.height
//
//            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
//            for (x in 0 until width){
//                for (y in 0 until height){
//                    bmp.setPixel(x, y, if(bitMatrix[x, y]) Color.BLACK else Color.WHITE)
//                }
//            }
//
//            iv_qr_code_dialog.setImageBitmap(bmp)
//        }catch (e: WriterException){
//
//        }
//    }
}